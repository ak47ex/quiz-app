package ru.pyatkinmv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pyatkinmv.dao.AnswerRepository;
import ru.pyatkinmv.dao.QuestionRepository;
import ru.pyatkinmv.dao.entities.Answer;
import ru.pyatkinmv.model.AnswerDto;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    static AnswerDto toDto(Answer answer) {
        return new AnswerDto(answer.getId(), answer.getText());
    }

    private static Answer toEntity(AnswerDto answerDto) {
        return Answer.builder()
                .text(answerDto.getText())
                .build();
    }

    @Transactional
    public List<AnswerDto> getByQuestionId(Integer questionId) {
        return questionRepository.findById(questionId)
                .map(
                        it -> it.getAnswers()
                                .stream()
                                .map(AnswerService::toDto)
                                .collect(toList())
                )
                .orElse(emptyList());
    }

    public Optional<AnswerDto> getById(Integer id) {
        return answerRepository.findById(id)
                .map(AnswerService::toDto);
    }

    @Transactional
    public AnswerDto create(AnswerDto answerDto) {
        return toDto(
                answerRepository.save(
                        toEntity(answerDto)
                )
        );
    }

    public boolean deleteById(Integer id) {
        try {
            answerRepository.deleteById(id);

            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }
}
