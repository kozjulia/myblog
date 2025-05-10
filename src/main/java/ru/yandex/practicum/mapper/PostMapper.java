package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import ru.yandex.practicum.dto.EditPostDto;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.model.Post;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.nonNull;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CommentMapper.class)
public interface PostMapper {

    String EMPTY = "";

    @Mapping(target = "textPreview", expression = "java(getTextPreview(post.getText()))")
    @Mapping(target = "textParts", expression = "java(getTextParts(post.getText()))")
    PostDto toPostDto(Post post);

    @Mapping(target = "text", source = "postDto.textPreview")
    @Mapping(target = "tagsAsText", expression = "java(getTagsAsText(postDto.getTags()))")
    EditPostDto toEditPostDto(PostDto postDto);

    Post toPost(PostDto postDto);

    List<PostDto> toPostDtos(List<Post> posts);

    @Named("getTextPreview")
    default String getTextPreview(String text) {
        return nonNull(text) ? text.split("\n")[0] : EMPTY;
    }

    @Named("getTextParts")
    default List<String> getTextParts(String text) {
        return Arrays.stream(text.split("\\n+")).toList();
    }

    @Named("getTagsAsText")
    default String getTagsAsText(String[] tags) {
        return nonNull(tags) ? String.join(",", tags) : EMPTY;
    }
}
