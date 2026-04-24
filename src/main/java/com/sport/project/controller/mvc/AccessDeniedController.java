package com.sport.project.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

    @GetMapping(path = "/access-denied")
    public String handleAccessDenied() {
        return "access-denied";
    }

}
