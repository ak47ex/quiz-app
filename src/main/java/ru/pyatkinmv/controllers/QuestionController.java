package ru.pyatkinmv.controllers;

import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pyatkinmv.model.AnswerDto;
import ru.pyatkinmv.model.QuestionDto;
import ru.pyatkinmv.service.AnswerService;
import ru.pyatkinmv.service.QuestionService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> getById(@PathVariable Integer id) {
        return questionService.getbyId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> create(@RequestBody QuestionBuildData qbd) {
        try {
            List<AnswerDto> answers = qbd.answersIds.stream()
                    .map(answerService::getById)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            AnswerDto correctAnswer = answerService.getById(qbd.getCorrectAnswerId()).get();
            return ResponseEntity.ok(questionService.create(new QuestionDto(
                    qbd.getId(),
                    qbd.getText(),
                    answers,
                    correctAnswer
            )));
        } catch (NoSuchElementException nsee) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        boolean deleted = questionService.deleteById(id);

        if (deleted) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class QuestionBuildData {
        private Integer id;
        private String text;
        private List<Integer> answersIds;
        private Integer correctAnswerId;
    }
}
