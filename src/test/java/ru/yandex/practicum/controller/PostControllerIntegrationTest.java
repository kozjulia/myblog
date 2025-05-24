package ru.yandex.practicum.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.BaseIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class PostControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @SneakyThrows
    void findPosts_shouldReturnHtmlWithPostsTest() {
        mockMvc.perform(get("/posts")
                        .param("search", "tag1"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts"))
                .andExpect(model().attributeExists("posts", "search", "paging"));
    }

    @Test
    @SneakyThrows
    void addPost_shouldAddPostToDatabaseAndRedirectTest() {
        MockMultipartFile image = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "IMAGE".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/posts")
                        .file(image)
                        .param("title", "Новый заголовок")
                        .param("text", "Содержание поста")
                        .param("tags", "tag"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/4"));

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM posts WHERE title = ?", Integer.class, "Новый заголовок");

        assertEquals(1, count);
    }

    @Test
    @SneakyThrows
    void deletePost_shouldRemovePostFromDatabaseAndRedirectTest() {
        mockMvc.perform(post("/posts/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }
}
