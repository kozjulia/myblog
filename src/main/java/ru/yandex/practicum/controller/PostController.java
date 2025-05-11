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
import ru.yandex.practicum.dto.EditPostDto;
import ru.yandex.practicum.dto.PagingDto;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.service.PostService;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController extends RedirectController {

    private final PostService service;

    /**
     * Cписок постов на странице ленты постов
     *
     * @param search     Строка с поиском по тегу поста (по умолчанию, пустая строка - все посты)
     * @param pageNumber Максимальное число постов на странице (по умолчанию, 10)
     * @param pageSize   Номер текущей страницы (по умолчанию, 1)
     * @param model      Модель
     * @return Шаблон "posts.html"
     */
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

    /**
     * Страница с постом
     *
     * @param postId Идентификатор поста
     * @param model  Модель
     * @return Шаблон "post.html"
     */
    @GetMapping("/{id}")
    public String getPostById(
            @PathVariable("id") Long postId,
            Model model) {

        PostDto post = service.getPostById(postId);
        model.addAttribute("post", post);

        return "post";
    }

    /**
     * Страница добавления поста
     *
     * @param model Модель
     * @return Шаблон "add-post.html"
     */
    @GetMapping("/add")
    public String getAddingForm(Model model) {
        model.addAttribute("post", null);

        return "add-post";
    }

    /**
     * Добавление поста
     *
     * @param title Название поста
     * @param text  Текст поста
     * @param image Файл картинки поста
     * @param tags  Список тегов поста (по умолчанию, пустая строка)
     * @return Редирект на созданный "/posts/{id}"
     */
    @PostMapping
    public String addPost(
            @RequestParam("title") String title,
            @RequestParam("text") String text,
            @RequestPart("image") MultipartFile image,
            @RequestParam(name = "tags", required = false) String tags) {

        Long postId = service.addPost(title, text, image, tags);

        return REDIRECT_POSTS + SLASH + postId;
    }

    /**
     * Увеличение/уменьшение числа лайков поста
     *
     * @param postId Идентификатор поста
     * @param like   Если true, то +1 лайк, если "false", то -1 лайк
     * @return Редирект на "/posts/{id}"
     */
    @PostMapping("/{id}/like")
    public String likePostById(
            @PathVariable("id") Long postId,
            @RequestParam("like") Boolean like
    ) {

        service.likePostById(postId, like);
        return REDIRECT_POSTS + SLASH + postId;
    }

    /**
     * Страница редактирования поста
     *
     * @param model  Модель
     * @param postId Идентификатор поста
     * @return Редирект на форму редактирования поста "add-post.html"
     */
    @GetMapping("/{id}/edit")
    public String getEditingForm(
            Model model,
            @PathVariable("id") Long postId
    ) {
        EditPostDto post = service.getEditPostDtoById(postId);
        model.addAttribute("post", post);

        return "add-post";
    }

    /**
     * Редактирование пост
     *
     * @param postId Идентификатор поста
     * @param title  Название поста
     * @param text   Текст поста
     * @param image  Файл картинки поста (класс MultipartFile, может быть null - значит, остается прежним)
     * @param tags   Список тегов поста (по умолчанию, пустая строка)
     * @return Редирект на отредактированный "/posts/{id}"
     */
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

    /**
     * Эндпоинт удаления поста
     *
     * @param postId Идентификатор поста
     * @return Редирект на "/posts"
     */
    @PostMapping(value = "/{id}/delete")
    public String deletePost(@PathVariable("id") Long postId) {
        service.deletePost(postId);

        return REDIRECT_POSTS;
    }
}
