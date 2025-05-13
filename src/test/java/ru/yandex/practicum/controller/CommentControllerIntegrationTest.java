package ru.yandex.practicum.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yandex.practicum.TestConstants.POST_ID;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.BaseIntegrationTest;

public class CommentControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @SneakyThrows
    void addComment_shouldAddCommentAndRedirectTest() {

        mockMvc.perform(post("/posts/{id}/comments", POST_ID)
                        .param("text", "Это тестовый комментарий"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));

        JdbcTemplate jdbcTemplate = webContext.getBean(JdbcTemplate.class);
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM comments WHERE post_id = ?", Integer.class, POST_ID);

        assertEquals(2, count);
    }
}
