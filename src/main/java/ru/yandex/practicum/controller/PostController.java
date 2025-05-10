package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.dto.PagingDto;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.service.PostService;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController extends RedirectController {

    private final PostService service;

    @GetMapping
    public String findPosts(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            Model model) {

        List<PostDto> posts = service.findPosts(search, pageNumber, pageSize);
        model.addAttribute("posts", posts);
        model.addAttribute("search", search);
        model.addAttribute("paging", new PagingDto(pageNumber, pageSize, posts.size()));

        return "posts";
    }

    @GetMapping("/{id}")
    public String getPostById(
            @PathVariable(name = "id") Long id,
            Model model) {

        PostDto post = service.getPostById(id);
        model.addAttribute("post", post);

        return "post";
    }

    @GetMapping("/add")
    public String getAddingForm(Model model) {
        model.addAttribute("post", null);

        return "add-post";
    }

    //починить
    @PostMapping
    public String addPost(@ModelAttribute PostDto postDto) {
        service.addPost(postDto);

        return REDIRECT_POSTS;
    }


    @PostMapping(value = "/{id}/delete", params = "_method=delete")
    public String deletePost(@PathVariable(name = "id") Long id) {
        service.deletePost(id);

        return REDIRECT_POSTS;
    }
}
