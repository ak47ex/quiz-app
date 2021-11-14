package ru.pyatkinmv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pyatkinmv.dao.QuestionAnswerRepository;
import ru.pyatkinmv.dao.QuizRepository;
import ru.pyatkinmv.dao.ShortcutRepository;
import ru.pyatkinmv.dao.entities.*;
import ru.pyatkinmv.model.QuestionDto;
import ru.pyatkinmv.model.QuizDto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuestionAnswerRepository questionAnswerRepository;
    private final QuizRepository quizRepository;
    private final ShortcutRepository shortcutRepository;

    @Transactional
    public Optional<QuizDto> getByShortcut(String shortcut) {
        return quizRepository.findByShortcut(shortcut)
                .map(this::toDto);
    }

    @Transactional
    public Optional<QuizDto> getByCreatorId(Integer creatorId) {
        return quizRepository.findByCreatorId(creatorId)
                .map(this::toDto);
    }

    @Transactional
    public Optional<QuizDto> getById(Integer id) {
        return quizRepository.findById(id).map(this::toDto);
    }

    @Transactional
    public QuizDto create(String secret, List<Integer> questionsIds) {
        List<QuestionAnswer> qaList = questionAnswerRepository.findAllByQuestionIds(questionsIds);
        if (qaList.isEmpty()) {
            throw new IllegalArgumentException("Not found questions!");
        }

        List<Question> questions = qaList.stream().map((qa) -> qa.getId().getQuestion()).collect(Collectors.toList());

        Shortcut shortcut = null;
        int tryCount = 0;
        while (shortcut == null) {
            tryCount++;
            try {
                shortcut = shortcutRepository.save(new Shortcut());
            } catch (Exception e) {
                if (tryCount > 5) throw e;
                System.out.println("Error on saving shortcut. Making another attempt");
            }
        }
        //FIXME: make shortcut generator inside quiz
        Quiz quiz = Quiz.builder().questions(questions).secret(secret).shortcut(shortcut.getShortcut()).build();
        //FIXME: make correct questions mapping
        return toDto(quizRepository.save(quiz));
    }

    private QuizDto toDto(Quiz quiz) {
        return QuizDto.builder()
                .id(quiz.getId())
                .shortcut(quiz.getShortcut())
                .secret(quiz.getSecret())
                .questions_ids(
                        quiz.getQuestions()
                                .stream()
                                .map(Question::getId)
                                .collect(toList())
                )
                .build();
    }

    private QuestionDto toDto(Question question, QuestionAnswer questionAnswer) {
        return QuestionDto.builder()
                .id(question.getId())
                .text(question.getText())
                .other_answers(
                        question.getAnswers()
                                .stream()
                                .filter((answer) -> !Objects.equals(answer.getId(), questionAnswer.getId().getAnswer().getId()))
                                .map(Answer::getText)
                                .collect(toList())
                )
                .correct_answer(questionAnswer.getId().getAnswer().getText())
                .build();
    }
}
