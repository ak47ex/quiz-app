package ru.pyatkinmv.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pyatkinmv.model.AnswerDto;
import ru.pyatkinmv.service.AnswerService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping(value = "/getByQuestionId", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AnswerDto>> getByQuestionId(@RequestParam(value = "id") Integer questionId) {
        return ResponseEntity.ok(answerService.getByQuestionId(questionId));
    }
}