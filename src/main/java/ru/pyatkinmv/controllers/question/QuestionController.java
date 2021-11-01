package ru.pyatkinmv.controllers.question;


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

    /**
     * create question request with following form:
     * {
     *   "text":"What color is sun?",
     *   "correct_answer":"yellow",
     *   "other_answers":["blue","green","red"]
     * }
     */
    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> create(@RequestBody CreateQuestionDto request) {
        QuestionDto dto = questionService.create(request.getText(), request.getCorrectAnswer(), request.getOtherAnswers());
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> getById(@PathVariable Integer id) {
        return questionService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
