package ru.pyatkinmv.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pyatkinmv.QuizApplication;
import ru.pyatkinmv.model.AnswerDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = QuizApplication.class)
class QuestionServiceTest {
    @Autowired
    private AnswerService answerService;

    @Test
    void createAndGetTest() {
//        AnswerDto answerDto = AnswerDto.builder().text("Some text").build();
//
//        answerService.create(answerDto);
//        List<AnswerDto> allProducts = answerService.getAll();
//
//        assertEquals(1, allProducts.size());
//        assertEquals(answerDto.getText(), allProducts.get(0).getText());
    }
}