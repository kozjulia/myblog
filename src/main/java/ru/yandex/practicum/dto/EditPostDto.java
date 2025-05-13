package ru.yandex.practicum.dto;

public record EditPostDto(

        Long id,
        String title,
        String text,
        String tagsAsText) {
}
