package ru.pyatkinmv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<Object> handleInvalidBody(IllegalArgumentException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleInvalidBody(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             @Nullable Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        log.error("Error during request handling", ex);
        return new ResponseEntity<>(ex.getMessage(), headers, status);
    }
}
