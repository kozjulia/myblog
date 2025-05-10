package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.service.ImageService;

@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    /**
     * Эндпоин, возвращающий набор байт картинки поста
     *
     * @param postId Идентификатор поста
     * @return Набор байт картинки поста
     */
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageByPostId(@PathVariable("id") Long postId) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageService.getImageByPostId(postId));
    }
}
