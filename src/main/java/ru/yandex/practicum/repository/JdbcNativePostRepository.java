package ru.yandex.practicum.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.Post;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcNativePostRepository implements PostRepository {

    private final RowMapper<Post> rowMapper = BeanPropertyRowMapper.newInstance(Post.class);

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Post> findPosts(String search, Integer pageSize, Integer offset) {

        return jdbcTemplate.query(
                //     + isEmpty(search) ? EMPTY : "where " + search +" in "
                """
                        select id, title, image_path, text, likes_count from posts 
                        limit ? 
                        offset ? 
                        """,
                rowMapper,
                pageSize,
                offset);
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select id, title, image_path, text, likes_count from posts "
                            + "where id = ? ",
                    rowMapper,
                    id
            ));

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long save(Post post) {
        String sqlQuery = "insert into posts (title, image_path, text, likes_count) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getImagePath());
            ps.setString(3, post.getText());
            ps.setLong(4, post.getLikesCount());
            return ps;
        }, keyHolder);
        return (Long) keyHolder.getKey();
    }

    @Override
    public void updateImagePath(Long postId, String imagePath) {
        jdbcTemplate.update("update posts SET image_path = ? where id = ?", imagePath, postId);
    }

    @Override
    public void updateLikesCount(Long postId, Integer likesCount) {
        jdbcTemplate.update("update posts SET likes_count = likes_count + ? where id = ?", likesCount, postId);
    }

    @Override
    public void update(Post post) {
        jdbcTemplate.update(
                "update posts SET title = ?, image_path = ?, text = ?, image_url = ? where id = ?",
                post.getTitle(),
                post.getImagePath(),
                post.getText(),
                post.getId()
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
