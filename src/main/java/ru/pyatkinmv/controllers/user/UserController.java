package ru.pyatkinmv.controllers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pyatkinmv.dao.entities.User;
import ru.pyatkinmv.service.UserService;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationDto> register(@RequestBody RegisterDto registerDto) {
        if (!registerDto.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Optional<User> registered = userService.register(registerDto.getUsername(), registerDto.getPassword(), registerDto.getToken());
        if (registered.isPresent()) {
            return userService.authorize(registerDto.getUsername(), registerDto.getPassword())
                    .map((token) -> ResponseEntity.ok(new AuthenticationDto(token))).orElse(ResponseEntity.badRequest().build());
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @PostMapping(value = "/auth", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationDto> auth(@RequestBody RegisterDto registerDto) {
        if (!registerDto.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<String> token = userService.authorize(registerDto.getUsername(), registerDto.getPassword());
        return token.map((tokenString) -> ResponseEntity.ok(new AuthenticationDto(tokenString))).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
}
