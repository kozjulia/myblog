package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RedirectController {

    protected final static String REDIRECT_POSTS = "redirect:/posts";

    @GetMapping("/")
    public String redirect() {
        return REDIRECT_POSTS;
    }
}
