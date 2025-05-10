package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.CommentDto;
import ru.yandex.practicum.model.Comment;

import java.util.List;

@Component
public class CommentMapper {

    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getPostId(),
                comment.getDescription()
        );
    }

    public List<CommentDto> toCommentDtos(List<Comment> comments) {
        return comments.stream()
                .map(this::toCommentDto)
                .toList();
    }

    public Comment toComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.id());
        comment.setPostId(commentDto.postId());
        comment.setDescription(commentDto.description());
        return comment;
    }

    public List<Comment> toComments(List<CommentDto> comments) {
        return comments.stream()
                .map(this::toComment)
                .toList();
    }
}
