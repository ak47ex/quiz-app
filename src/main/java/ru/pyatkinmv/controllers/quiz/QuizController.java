package ru.pyatkinmv.controllers.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pyatkinmv.model.QuizDto;
import ru.pyatkinmv.service.QuizService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping(value = "/getByShortcut/{shortcut}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizDto> getByShortcut(@PathVariable(value = "shortcut") String shortcut) {
        return quizService.getByShortcut(shortcut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/getByCreator/{creatorId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizDto> getByCreator(@PathVariable(value = "creatorId") Integer creatorId) {
        return quizService.getByCreatorId(creatorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/create", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizDto> create(@RequestBody CreateQuizDto request) {
        try {
            QuizDto dto = quizService.create(request.getSecret(), request.getQuestionsIds());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
