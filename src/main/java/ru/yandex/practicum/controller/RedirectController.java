package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RedirectController {

    protected static final String REDIRECT_POSTS = "redirect:/posts";
    protected static final String SLASH = "/";

    /**
     * Редирект на "/posts"
     *
     * @return Шаблон "posts.html"
     */
    @GetMapping("/")
    public String redirect() {
        return REDIRECT_POSTS;
    }
}
