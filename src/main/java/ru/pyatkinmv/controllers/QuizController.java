package ru.pyatkinmv.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pyatkinmv.model.QuizDto;
import ru.pyatkinmv.service.QuizService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping(value = "/getByShortcut", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizDto> getByShortcut(@RequestParam(value = "shortcut") String shortcut) {
        return quizService.getByShortcut(shortcut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/getByCreator", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizDto> getByShortcut(@RequestParam(value = "creatorId") Integer creatorId) {
        return quizService.getByCreatorId(creatorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
