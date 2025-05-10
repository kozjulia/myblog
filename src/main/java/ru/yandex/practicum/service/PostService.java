package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.repository.PostRepository;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.PostMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private static final String IMAGE_UPLOAD_ERROR_TEMPLATE = "Фото для поста с id = {} не получилось сохранить";

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public List<PostDto> findPosts(String search, Integer pageNumber, Integer pageSize) {
        Integer offset = (pageNumber - 1) * pageSize;

        return postRepository.findPosts(search, pageSize, offset).stream()
                .map(postMapper::toPostDto)
                .toList();
    }

    public PostDto getPostById(Long id) {
        return postRepository.getPostById(id)
                .map(postMapper::toPostDto)
                .orElseThrow(() -> new NotFoundException("Поста с id = " + id + " не существует"));
    }

    public Long addPost(String title, String text, MultipartFile image, String tags) {

        Post post = Post.builder()
                .title(title)
                .text(text)
                .likesCount(0L)
                .tags(Arrays.stream(tags.split(" ")).toList())
                .build();

        Long postId = postRepository.save(post);

        if (image.isEmpty()) {
            return postId;
        }
        try {
            String imagePath = "image-post-" + postId + "." + getExtension(image.getOriginalFilename());

            File file = new File(imagePath);
            image.transferTo(file);

            postRepository.updateImagePath(postId, imagePath);
        } catch (IOException e) {
            log.error(IMAGE_UPLOAD_ERROR_TEMPLATE, postId);
        }

        return postId;
    }

    public void likePostById(Long postId, Boolean like) {
        postRepository.updateLikesCount(postId, like ? 1 : -1);
    }

    public void editPost(Long postId, String title, String text, MultipartFile image, String tags) {

        PostDto postDto = getPostById(postId);
        String imagePath = postDto.imagePath();

        if (!image.isEmpty()) {
            try {
                imagePath = "image-post-" + postId + "." + getExtension(image.getOriginalFilename());

                File file = new File(imagePath);
                image.transferTo(file);
            } catch (IOException e) {
                log.error(IMAGE_UPLOAD_ERROR_TEMPLATE, postId);
            }
        }

        Post post = Post.builder()
                .id(postId)
                .title(title)
                .imagePath(imagePath)
                .text(text)
                .tags(isEmpty(tags) ? postDto.tags() : Arrays.stream(tags.split(" ")).toList())
                .build();

        postRepository.update(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
