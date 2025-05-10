package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.dto.CommentDto;
import ru.yandex.practicum.service.CommentService;

import java.util.List;

//@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    @GetMapping
    public String comments(Model model) {
        List<CommentDto> comments = service.findAll();
        model.addAttribute("comments", comments);

        return "comments";
    }

    @PostMapping
    public String save(@ModelAttribute CommentDto commentDto) {
        service.addComment(commentDto);

        return "redirect:/comments";
    }

    @PostMapping(value = "/{id}", params = "_method=delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteComment(id);

        return "redirect:/comments";
    }
}
