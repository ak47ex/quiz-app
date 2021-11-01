package ru.pyatkinmv.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.pyatkinmv.QuizApplication;
import ru.pyatkinmv.model.AnswerDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = QuizApplication.class)
class AnswerServiceTest {
    @Autowired
    private AnswerService answerService;

    @Test
    @Transactional
    void createGetAndDeleteTest() {
        AnswerDto answerDto = AnswerDto.builder()
                .text("text")
                .build();
        AnswerDto savedAnswer = answerService.create(answerDto);

        Optional<AnswerDto> getAnswerResult = answerService.getById(savedAnswer.getId());
        assertTrue(getAnswerResult.isPresent());
        assertEquals(savedAnswer, getAnswerResult.get());

        boolean deleted = answerService.deleteById(savedAnswer.getId());
        assertTrue(deleted);

        Optional<AnswerDto> getAnswerAfterDeletion = answerService.getById(savedAnswer.getId());
        assertTrue(getAnswerAfterDeletion.isEmpty());

        deleted = answerService.deleteById(savedAnswer.getId());
        assertFalse(deleted);
    }
}