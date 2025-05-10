package ru.yandex.practicum.dto;

import java.util.List;

public record PostDto(
        Long id,
        String title,
        String imagePath,
        String textPreview,
        Long likesCount,
        List<String> tags,
        List<CommentDto> comments) {
}
