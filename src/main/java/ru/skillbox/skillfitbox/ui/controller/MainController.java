package ru.skillbox.skillfitbox.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/ui")
    public String uiIndex() {
        return "index";
    }
}
