package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.model.Post;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CommentMapper.class)
public interface PostMapper {

    PostDto toPostDto(Post post);

    Post toPost(PostDto postDto);
}
