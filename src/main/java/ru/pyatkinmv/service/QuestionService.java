package ru.pyatkinmv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pyatkinmv.dao.QuestionRepository;
import ru.pyatkinmv.dao.entities.Answer;
import ru.pyatkinmv.dao.entities.Question;
import ru.pyatkinmv.model.AnswerDto;
import ru.pyatkinmv.model.QuestionDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    static QuestionDto toDto(Question question) {
        List<AnswerDto> answers = question.getAnswers()
                .stream()
                .map(AnswerService::toDto)
                .collect(Collectors.toList());

        return new QuestionDto(
                question.getId(),
                question.getText(),
                answers,
                AnswerService.toDto(question.getCorrectAnswer())
        );
    }

    private static Question toEntity(QuestionDto questionDto) {
        List<Answer> answers = questionDto.getAnswers()
                .stream()
                .map(AnswerService::toEntity)
                .collect(Collectors.toList());

        return Question.builder()
                .id(questionDto.getId())
                .answers(answers)
                .text(questionDto.getText())
                .correctAnswer(AnswerService.toEntity(questionDto.getCorrectAnswer()))
                .build();
    }

    @Transactional
    public Optional<QuestionDto> getbyId(Integer id) {
        return questionRepository.findById(id).map(QuestionService::toDto);
    }

    @Transactional
    public QuestionDto create(QuestionDto questionDto) {
        return toDto(
                questionRepository.save(
                        toEntity(questionDto)
                )
        );
    }

    @Transactional
    public boolean deleteById(Integer id) {
        try {
            questionRepository.deleteById(id);

            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }
}