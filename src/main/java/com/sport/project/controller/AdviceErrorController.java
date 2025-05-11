package com.sport.project.controller;

import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@ControllerAdvice
@Slf4j
public class AdviceErrorController {

    @ExceptionHandler(EntityNotFoundException.class)
    public void printEntityNotFoundException(EntityNotFoundException exception) {
        log.info(exception.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public void printEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        log.info(exception.getMessage());
    }

}
