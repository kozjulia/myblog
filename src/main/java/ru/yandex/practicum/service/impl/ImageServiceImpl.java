package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final PostServiceImpl postService;

    @Override
    public byte[] getImageByPostId(Long postId) {

        String imagePath = postService.getPostById(postId).getImagePath();

        try {
            Path path = Path.of("uploads").resolve(imagePath);

            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("Фото для поста с id = {} не получилось загрузить", postId);
        }

        return new byte[0];
    }
}
