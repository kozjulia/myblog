package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.dto.PagingDto;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.service.PostService;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController extends RedirectController {

    private static final String SLASH = "/";

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
            @PathVariable("id") Long postId,
            Model model) {

        PostDto post = service.getPostById(postId);
        model.addAttribute("post", post);

        return "post";
    }

    @GetMapping("/add")
    public String getAddingForm(Model model) {
        model.addAttribute("post", null);

        return "add-post";
    }

    @PostMapping
    public String addPost(
            @RequestParam("title") String title,
            @RequestParam("text") String text,
            @RequestPart("image") MultipartFile image,
            @RequestParam(name = "tags", required = false) String tags) {

        Long postId = service.addPost(title, text, image, tags);

        return REDIRECT_POSTS + SLASH + postId;
    }

    @PostMapping("/{id}/like")
    public String likePostById(
            @PathVariable("id") Long postId,
            @RequestParam("like") Boolean like
    ) {

        service.likePostById(postId, like);
        return REDIRECT_POSTS + SLASH + postId;
    }

    @GetMapping("/{id}/edit")
    public String getEditingForm(
            Model model,
            @PathVariable("id") Long postId
    ) {
        PostDto post = service.getPostById(postId);
        model.addAttribute("post", post);

        return "add-post";
    }

    @PostMapping("{id}")
    public String editPost(
            @PathVariable("id") Long postId,
            @RequestParam("title") String title,
            @RequestParam("text") String text,
            @RequestPart(name = "image", required = false) MultipartFile image,
            @RequestParam(name = "tags", required = false) String tags) {

        service.editPost(postId, title, text, image, tags);

        return REDIRECT_POSTS + SLASH + postId;
    }

    @PostMapping(value = "/{id}/delete")
    public String deletePost(@PathVariable("id") Long postId) {
        service.deletePost(postId);

        return REDIRECT_POSTS;
    }
}
