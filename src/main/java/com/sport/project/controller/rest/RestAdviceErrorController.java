package com.sport.project.controller.rest;

import com.sport.project.dto.ErrorResponseDto;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * Контроллер, который перехватывает ошибки
 * */
@RestController
@RestControllerAdvice
@Slf4j
public class RestAdviceErrorController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNotFound(EntityNotFoundException ex) {
        log.info("[REST] Entity not found");
        ErrorResponseDto build = ErrorResponseDto.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .trace(ex.getStackTrace())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(build);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<?> handleExists(EntityAlreadyExistsException ex) {
        log.info("[REST] Entity already exists");
        ErrorResponseDto build = ErrorResponseDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .trace(ex.getStackTrace())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(build);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegaArgumentException(IllegalArgumentException ex) {
        ErrorResponseDto build = ErrorResponseDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .trace(ex.getStackTrace())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(build);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ErrorResponseDto build = ErrorResponseDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .trace(ex.getStackTrace())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(build);
    }

}
