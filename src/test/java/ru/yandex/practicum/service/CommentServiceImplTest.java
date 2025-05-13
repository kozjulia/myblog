package ru.yandex.practicum.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.repository.CommentRepository;
import ru.yandex.practicum.service.impl.CommentServiceImpl;

import static org.mockito.Mockito.verify;
import static ru.yandex.practicum.TestConstants.COMMENT_ID;
import static ru.yandex.practicum.TestConstants.COMMENT_TEXT;
import static ru.yandex.practicum.TestConstants.POST_ID;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Test
    void addCommentTest() {
        commentService.addComment(POST_ID, COMMENT_TEXT);

        verify(commentRepository).save(POST_ID, COMMENT_TEXT);
    }

    @Test
    void editCommentTest() {
        commentService.editComment(COMMENT_ID, COMMENT_TEXT);

        verify(commentRepository).update(COMMENT_ID, COMMENT_TEXT);
    }

    @Test
    void deleteCommentTest() {
        commentService.deleteComment(COMMENT_ID);

        verify(commentRepository).deleteById(COMMENT_ID);
    }
}