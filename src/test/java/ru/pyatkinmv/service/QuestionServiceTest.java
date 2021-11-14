package ru.pyatkinmv.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.pyatkinmv.QuizApplication;
import ru.pyatkinmv.model.QuestionDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = QuizApplication.class)
class QuestionServiceTest {
    @Autowired
    private QuestionService questionService;

    @Test
    @Transactional
    void createGetAndDeleteTest() {
        String text = "hello world";
        String correctAnswer = "correct answer";
        String invalidAnswer = "invalid answer";
        QuestionDto savedQuestion = questionService.create(text, correctAnswer, List.of(invalidAnswer));

        Optional<QuestionDto> getQuestionResult = questionService.getById(savedQuestion.getId());
        assertTrue(getQuestionResult.isPresent());
        assertEquals(savedQuestion, getQuestionResult.get());

        assertEquals(getQuestionResult.get().getText(), text);
        assertEquals(getQuestionResult.get().getCorrect_answer(), correctAnswer);

        boolean deleted = questionService.deleteById(savedQuestion.getId());
        assertTrue(deleted);

        Optional<QuestionDto> getQuestionAfterDeletion = questionService.getById(savedQuestion.getId());
        assertTrue(getQuestionAfterDeletion.isEmpty());

        deleted = questionService.deleteById(savedQuestion.getId());
        assertFalse(deleted);
    }
}