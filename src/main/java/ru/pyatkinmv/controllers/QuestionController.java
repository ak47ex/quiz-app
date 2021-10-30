package ru.pyatkinmv.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pyatkinmv.model.QuestionDto;
import ru.pyatkinmv.service.QuestionService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> getById(@PathVariable Integer id) {
        return questionService.getbyId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> create(@RequestBody QuestionDto questionDto) {
        return ResponseEntity.ok(questionService.create(questionDto));
    }

    @DeleteMapping(value = "/{id}}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        boolean deleted = questionService.deleteById(id);

        if (deleted) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
