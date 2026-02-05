package org.example.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DebugExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handle(Exception ex) {
        ex.printStackTrace();
    }
}

