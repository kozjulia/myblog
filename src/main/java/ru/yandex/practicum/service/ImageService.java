package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final PostService postService;

    public byte[] getImageByPostId(Long postId) {

        String imagePath = postService.getPostById(postId).imagePath();

        try {
            Path path = Paths.get(imagePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("Фото для поста с id = {} не получилось загрузить", postId);
        }
        return new byte[0];
    }
}
