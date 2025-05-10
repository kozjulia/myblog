package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.repository.PostRepository;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.PostMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

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
                .orElseThrow(() -> new NotFoundException("Поста с id = " + id + "не существует"));
    }

    public void addPost(PostDto postDto) {
        postRepository.save(postMapper.toPost(postDto));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
