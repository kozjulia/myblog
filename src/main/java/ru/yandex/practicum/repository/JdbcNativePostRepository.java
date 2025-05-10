package ru.yandex.practicum.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class JdbcNativePostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Post> findPosts(String search, Integer pageSize, Integer offset) {

        return jdbcTemplate.query(
                //     + isEmpty(search) ? EMPTY : "where " + search +" in "
                """
                        select id, title, image_path, text, likes_count from posts "
                        limit :pageSize
                        offset :offset
                        """,
                (rs, rowNum) -> new Post(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("image_path"),
                        rs.getString("text"),
                        rs.getLong("likes_count"),
                        Collections.emptyList(),
                        Collections.emptyList()
                ),
                pageSize,
                offset);
    }

    @Override
    public Optional<Post> getPostById(Long id) {

        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select id, title, image_path, text, likes_count from posts "
                        + "where id = :id",
                Post.class,
                id
        ));
    }

    @Override
    public void save(Post post) {
        jdbcTemplate.update(
                "insert into posts (id, title, image_path, text, likes_count) values (?, ?, ?, ?, ?)",
                post.getId(),
                post.getTitle(),
                post.getImagePath(),
                post.getText(),
                post.getLikesCount()
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(
                "delete from posts where id = ?",
                id
        );
    }
}
