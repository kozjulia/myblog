package ru.yandex.practicum.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.model.Post;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final CommentMapper commentMapper;

    public PostDto toPostDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getImagePath(),
                post.getText(),
                post.getLikesCount(),
                commentMapper.toCommentDtos(post.getComments())
        );
    }

    public Post toPost(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.id());
        post.setTitle(postDto.title());
        post.setImagePath(postDto.imagePath());
        post.setText(postDto.textPreview());
        post.setLikesCount(postDto.likesCount());
        post.setComments(commentMapper.toComments(postDto.comments()));
        return post;
    }
}
