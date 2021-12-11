package ru.pyatkinmv.controllers;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

public abstract class BaseController {

    @NonNull
    protected Optional<String> getToken(String auth) {
        Authorization a = Authorization.parse(auth);
        String token = null;
        if (a instanceof Authorization.Bearer) {
            token = ((Authorization.Bearer) a).token;
        }
        if (token == null || token.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(token);
    }
}
