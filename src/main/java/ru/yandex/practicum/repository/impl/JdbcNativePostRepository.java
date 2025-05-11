package ru.yandex.practicum.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.repository.PostRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isEmpty;

@Repository
@RequiredArgsConstructor
public class JdbcNativePostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Post> findPosts(String search, Integer pageSize, Integer offset) {
        return jdbcTemplate.query(
                "SELECT id, title, image_path, text, likes_count, array_to_string(tags,',') AS tags FROM posts "
                        + (isEmpty(search) ? "" : "WHERE array_position(tags,'" + search + "') > 0 ")
                        + "LIMIT ? "
                        + "OFFSET ? ",
                (rs, rowNum) -> new Post(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("image_path"),
                        rs.getString("text"),
                        rs.getLong("likes_count"),
                        rs.getString("tags").split(","),
                        Collections.EMPTY_LIST
                ),
                pageSize,
                offset);
    }

    @Override
    public Optional<Post> getPostById(Long postId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT id, title, image_path, text, likes_count, array_to_string(tags,',') AS tags FROM posts "
                            + "WHERE id = ? ",
                    (rs, rowNum) -> new Post(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("image_path"),
                            rs.getString("text"),
                            rs.getLong("likes_count"),
                            rs.getString("tags").split(","),
                            Collections.EMPTY_LIST
                    ),
                    postId
            ));

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long save(Post post) {
        String sqlQuery = "INSERT INTO posts (title, image_path, text, likes_count, tags) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getImagePath());
            ps.setString(3, post.getText());
            ps.setLong(4, post.getLikesCount());
            ps.setArray(5, connection.createArrayOf("VARCHAR", post.getTags()));
            return ps;
        }, keyHolder);
        return (Long) keyHolder.getKeyList().getFirst().get("id");
    }

    @Override
    public void updateImagePath(Long postId, String imagePath) {
        jdbcTemplate.update("UPDATE posts SET image_path = ? WHERE id = ?", imagePath, postId);
    }

    @Override
    public void updateLikesCount(Long postId, Integer likesCount) {
        jdbcTemplate.update("UPDATE posts SET likes_count = likes_count + ? WHERE id = ?", likesCount, postId);
    }

    @Override
    public void update(Post post) {
        jdbcTemplate.update(
                "UPDATE posts SET title = ?, image_path = ?, text = ? WHERE id = ?",
                post.getTitle(),
                post.getImagePath(),
                post.getText(),
                post.getId()
        );
    }

    @Override
    public void deleteById(Long postId) {
        jdbcTemplate.update(
                "DELETE FROM posts WHERE id = ?",
                postId
        );
    }
}
