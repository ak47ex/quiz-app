package ru.pyatkinmv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pyatkinmv.dao.QuestionAnswerRepository;
import ru.pyatkinmv.dao.QuestionRepository;
import ru.pyatkinmv.dao.QuizRepository;
import ru.pyatkinmv.dao.entities.Question;
import ru.pyatkinmv.dao.entities.QuestionAnswer;
import ru.pyatkinmv.dao.entities.Quiz;
import ru.pyatkinmv.model.QuestionDto;
import ru.pyatkinmv.model.QuizDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuestionAnswerRepository questionAnswerRepository;
    private final QuizRepository quizRepository;

    @Transactional
    public Optional<QuizDto> getByShortcut(String shortcut) {
        return quizRepository.findByShortcut(shortcut)
                .map(QuizService::toDto);
    }

    @Transactional
    public Optional<QuizDto> getByCreatorId(Integer creatorId) {
        return quizRepository.findByCreatorId(creatorId)
                .map(QuizService::toDto);
    }

    @Transactional
    public QuizDto create(String secret, List<Integer> questionsIds) {
        List<QuestionAnswer> qaList = questionAnswerRepository.findAllByQuestionIds(questionsIds);
        if (qaList.isEmpty()) {
            throw new IllegalArgumentException("Not found questions!");
        }

        List<Question> questions = qaList.stream().map((qa) -> qa.getId().getQuestion()).collect(Collectors.toList());
        //FIXME: make shortcut generator inside quiz
        Quiz quiz = Quiz.builder().questions(questions).secret(secret).build();
        //FIXME: make correct questions mapping
        return toDto(quizRepository.save(quiz));
    }

    private static QuizDto toDto(Quiz quiz) {
        return QuizDto.builder()
                .id(quiz.getId())
                .shortcut(quiz.getShortcut())
                .secret(quiz.getSecret())
                .questions(
                        quiz.getQuestions()
                                .stream()
                                .map(QuizService::toDto)
                                .collect(toList())
                )
                .build();
    }

    private static QuestionDto toDto(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .text(question.getText())
                .answers(
                        question.getAnswers()
                                .stream()
                                .map(AnswerService::toDto)
                                .collect(toList())
                )
//                .correctAnswer(AnswerService.toDto(question.getCorrectAnswer()))
                .build();
    }
}
