package com.sport.project.controller.rest;

import com.sport.project.dto.ErrorResponseDto;
import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
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

//    @ExceptionHandler(EntityNotFoundException.class)
//    public void printLogEntityNotFoundRest(EntityNotFoundException ex) {
//        log.info("[REST] Entity not found");
//    }
//
//    @ExceptionHandler(EntityAlreadyExistsException.class)
//    public void printLogEntityAlreadyExists(EntityAlreadyExistsException ex) {
//        log.info("Entity already exists");
//    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponseDto handleNotFound(IOException ex) {
        log.info("[REST] Entity not found");
        return ErrorResponseDto.builder()
                .message(ex.getMessage())
                .trace(ex.getStackTrace())
                .build();
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ErrorResponseDto handleExists(EntityAlreadyExistsException ex) {
        log.info("Entity already exists");
        return ErrorResponseDto.builder()
                .message(ex.getMessage())
                .trace(ex.getStackTrace())
                .build();
    }

}
