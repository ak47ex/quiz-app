package ru.pyatkinmv.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AnswerDto> getById(@PathVariable Integer id) {
        return answerService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AnswerDto> create(@RequestBody AnswerDto answerDto) {
        return ResponseEntity.ok(answerService.create(answerDto));
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        boolean deleted = answerService.deleteById(id);

        if (deleted) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}