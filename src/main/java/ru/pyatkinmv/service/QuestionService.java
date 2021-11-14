package ru.pyatkinmv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pyatkinmv.dao.QuestionAnswerRepository;
import ru.pyatkinmv.dao.QuestionRepository;
import ru.pyatkinmv.dao.entities.Answer;
import ru.pyatkinmv.dao.entities.Question;
import ru.pyatkinmv.dao.entities.QuestionAnswer;
import ru.pyatkinmv.model.AnswerDto;
import ru.pyatkinmv.model.QuestionDto;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionAnswerRepository questionAnswerRepository;

    @Transactional
    public Optional<QuestionDto> getById(Integer id) {
        return questionAnswerRepository.findByQuestionId(id).map(QuestionService::toDto);
    }

    @Transactional
    public boolean deleteById(Integer id) {
        try {
            //TODO: сделать удаление в одну команду
            return questionAnswerRepository.findByQuestionId(id).map((qa) -> {
                questionAnswerRepository.delete(qa);
                return true;
            }).orElse(false);
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Transactional
    public QuestionDto create(String text, String correctAnswer, Collection<String> otherAnswers) {
        final QuestionAnswer.QuestionAnswerId.QuestionAnswerIdBuilder questionAnswerBuilder = QuestionAnswer.QuestionAnswerId.builder();
        final Answer.AnswerBuilder answerBuilder = Answer.builder();
        final Question.QuestionBuilder questionBuilder = Question.builder();

        final List<Answer> answers = new ArrayList<>(otherAnswers.size() + 1);
        {
            final Answer answer = answerBuilder.text(correctAnswer).build();
            answers.add(answer);
            questionAnswerBuilder.answer(answer);
        }
        for (String a : otherAnswers) {
            final Answer answer = answerBuilder.text(a).build();
            answers.add(answer);
        }
        final Question q = questionBuilder.text(text).answers(answers).build();
        final Question question = questionRepository.save(q);

        final QuestionAnswer.QuestionAnswerId qa = questionAnswerBuilder.question(question).build();

        final QuestionAnswer questionAnswer = questionAnswerRepository.save(QuestionAnswer.builder().id(qa).build());
        return toDto(questionAnswer);
    }

    public static QuestionDto toDto(QuestionAnswer qa) {
        return QuestionDto.builder()
                .id(qa.getId().getQuestion().getId())
                .text(qa.getId().getQuestion().getText())
                .other_answers(
                        qa.getId()
                                .getQuestion()
                                .getAnswers()
                                .stream()
                                .filter((answer) -> !Objects.equals(answer.getId(), qa.getId().getAnswer().getId()))
                                .map((Answer::getText))
                                .collect(Collectors.toList())
                )
                .correct_answer(qa.getId().getAnswer().getText())
                .build();
    }

    private static AnswerDto toDto(Answer answer) {
        return AnswerService.toDto(answer);
    }
}
