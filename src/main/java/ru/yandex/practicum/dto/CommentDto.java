package ru.yandex.practicum.dto;

public record CommentDto(
        Long id,
        Long postId,
        String description) {
}
