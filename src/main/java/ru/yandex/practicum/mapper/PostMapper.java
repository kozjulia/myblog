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
    String NEW_LINE = "\n";

    @Mapping(target = "textPreview", expression = "java(getTextPreview(post.getText()))")
    @Mapping(target = "textParts", expression = "java(getTextParts(post.getText()))")
    PostDto toPostDto(Post post);

    @Mapping(target = "text", expression = "java(getText(postDto.getTextParts()))")
    @Mapping(target = "tagsAsText", expression = "java(getTagsAsText(postDto.getTags()))")
    EditPostDto toEditPostDto(PostDto postDto);

    List<PostDto> toPostDtos(List<Post> posts);

    @Named("getTextPreview")
    default String getTextPreview(String text) {
        return nonNull(text) ? text.split(NEW_LINE)[0] : EMPTY;
    }

    @Named("getTextParts")
    default List<String> getTextParts(String text) {
        return Arrays.stream(text.split(NEW_LINE)).toList();
    }

    @Named("getText")
    default String getText(List<String> textParts) {
        return nonNull(textParts) ? String.join(NEW_LINE, textParts) : EMPTY;
    }

    @Named("getTagsAsText")
    default String getTagsAsText(String[] tags) {
        return nonNull(tags) ? String.join(",", tags) : EMPTY;
    }
}
