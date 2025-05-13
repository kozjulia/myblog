package ru.yandex.practicum.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.Comment;
import ru.yandex.practicum.repository.CommentRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcNativeCommentRepository implements CommentRepository {

    private final RowMapper<Comment> rowMapper = BeanPropertyRowMapper.newInstance(Comment.class);

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Long postId, String text) {
        jdbcTemplate.update(
                "INSERT INTO comments (post_id, text) VALUES (?, ?)",
                postId,
                text
        );
    }

    @Override
    public void update(Long commentId, String text) {
        jdbcTemplate.update(
                "UPDATE comments SET text = ? WHERE id = ?",
                text,
                commentId
        );
    }

    @Override
    public void deleteById(Long commentId) {
        jdbcTemplate.update(
                "DELETE FROM comments WHERE id = ?",
                commentId
        );
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        return jdbcTemplate.query(
                "SELECT * FROM comments WHERE post_id = ?",
                rowMapper,
                postId
        );
    }
}
