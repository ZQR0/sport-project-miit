package com.sport.project.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;

public class CustomErrorController {

    private static final String PATH = "/error";

    @GetMapping(path = PATH)
    public String handleError() {
        return "error";
    }

    @GetMapping(path = "/already-exists")
    public String handleAlreadyExists() {
        return "already_exists";
    }

}
