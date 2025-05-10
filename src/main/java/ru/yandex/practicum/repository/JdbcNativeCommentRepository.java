package ru.yandex.practicum.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.Comment;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class JdbcNativeCommentRepository implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Comment comment) {
        jdbcTemplate.update(
                "insert into comments (id, post_id, description) values (?, ?, ?)",
                comment.getId(),
                comment.getPostId(),
                comment.getDescription()
        );
    }

    @Override
    public List<Comment> findAll() {
        return jdbcTemplate.query(
                "select id, post_id, description from comments",
                (rs, rowNum) -> new Comment(
                        rs.getLong("id"),
                        rs.getLong("post_id"),
                        rs.getString("description")
                ));
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(
                "delete from comments where id = ?",
                id
        );
    }
}
