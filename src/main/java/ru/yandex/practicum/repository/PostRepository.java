package ru.yandex.practicum.repository;

import ru.yandex.practicum.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    List<Post> findPosts(String search, Integer pageSize, Integer offset);
    Optional<Post> getPostById(Long id);
    void save(Post post);
    void deleteById(Long id);
}
