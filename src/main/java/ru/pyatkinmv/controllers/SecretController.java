package ru.pyatkinmv.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pyatkinmv.model.SecretDto;
import ru.pyatkinmv.service.SecretService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/secret")
@RequiredArgsConstructor
public class SecretController {
    private final SecretService secretService;


    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SecretDto> getById(@PathVariable Integer id) {
        return secretService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SecretDto> create(@RequestBody SecretDto secretDto) {
        return ResponseEntity.ok(secretService.create(secretDto));
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        boolean deleted = secretService.deleteById(id);

        if (deleted) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
