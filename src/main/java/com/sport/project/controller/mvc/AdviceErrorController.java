package com.sport.project.controller;

import com.sport.project.exception.EntityAlreadyExistsException;
import com.sport.project.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Controller
@ControllerAdvice
@Slf4j
public class AdviceErrorController {

    @ExceptionHandler(EntityNotFoundException.class)
    public void printEntityNotFoundException(EntityNotFoundException exception, HttpServletResponse response) {
        log.info(exception.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public void printEntityAlreadyExistsException(EntityAlreadyExistsException exception, HttpServletResponse response) {
        log.info(exception.getMessage());
        try {
            response.sendRedirect("/already-exists");
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }
    }

}
