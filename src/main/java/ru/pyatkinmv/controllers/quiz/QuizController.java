package ru.pyatkinmv.controllers.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pyatkinmv.controllers.BaseController;
import ru.pyatkinmv.dao.entities.User;
import ru.pyatkinmv.model.QuizDto;
import ru.pyatkinmv.service.QuizService;
import ru.pyatkinmv.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController extends BaseController {
    private final UserService userService;
    private final QuizService quizService;

    @GetMapping(value = "/getByShortcut/{shortcut}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizDto> getByShortcut(@PathVariable(value = "shortcut") String shortcut) {
        return quizService.getByShortcut(shortcut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/getByCreator/{creatorId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuizDto>> getByCreatorToken(@PathVariable(value = "creatorId") Integer creatorId) {
        return ResponseEntity.ok(quizService.getByCreatorId(creatorId));
    }


    @PostMapping(value = "/getMy", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuizDto>> getMy(@RequestHeader("Authorization") String auth) {
        Optional<String> token = getToken(auth);
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return userService.getByToken(token.get())
                .map((user) -> quizService.getByCreatorId(user.getId()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping(value = "/create", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizDto> create(@RequestHeader("Authorization") String auth, @RequestBody CreateQuizDto request) {
        Optional<String> token = getToken(auth);
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        System.out.println("create " + token);
        Optional<User> user = userService.getByToken(token.get()).or(() -> userService.createNewUser(token.get()));
        try {
            QuizDto dto = quizService.create(user.get(), request.getSecret(), request.getQuestionsIds());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizDto> getById(@PathVariable Integer id) {
        return quizService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
