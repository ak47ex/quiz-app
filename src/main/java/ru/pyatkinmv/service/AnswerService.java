package ru.pyatkinmv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pyatkinmv.dao.QuestionRepository;
import ru.pyatkinmv.dao.entities.Answer;
import ru.pyatkinmv.model.AnswerDto;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final QuestionRepository questionRepository;

    static AnswerDto toDto(Answer answer) {
        return new AnswerDto(answer.getId(), answer.getText());
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
}
