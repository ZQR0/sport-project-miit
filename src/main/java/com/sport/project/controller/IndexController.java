package com.sport.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(path = "/index")
    public String handleIndex() {
        return "index.html";
    }

}
