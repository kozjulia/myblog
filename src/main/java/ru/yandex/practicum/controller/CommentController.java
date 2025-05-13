package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.service.CommentService;

@Controller
@RequestMapping("/posts/{id}/comments")
@RequiredArgsConstructor
public class CommentController extends RedirectController {

    private final CommentService service;

    /**
     * Эндпоинт добавления комментария к посту
     *
     * @param postId Идентификатор поста
     * @param text   Текст комментария
     * @return Редирект на "/posts/{id}"
     */
    @PostMapping
    public String addComment(
            @PathVariable("id") Long postId,
            @RequestParam("text") String text
    ) {
        service.addComment(postId, text);

        return REDIRECT_POSTS + SLASH + postId;
    }

    /**
     * Эндпоинт редактирования комментария
     *
     * @param postId    Идентификатор поста
     * @param commentId Идентификатор комментария
     * @param text      Текст комментария
     * @return Редирект на "/posts/{id}"
     */
    @PostMapping("/{commentId}")
    public String editComment(
            @PathVariable("id") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestParam("text") String text
    ) {
        service.editComment(commentId, text);

        return REDIRECT_POSTS + SLASH + postId;
    }

    /**
     * Эндпоинт удаления комментария
     *
     * @param postId    Идентификатор поста
     * @param commentId Идентификатор комментария
     * @return Редирект на "/posts/{id}"
     */
    @PostMapping(value = "/{commentId}/delete")
    public String delete(
            @PathVariable("id") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        service.deleteComment(commentId);

        return REDIRECT_POSTS + SLASH + postId;
    }
}
