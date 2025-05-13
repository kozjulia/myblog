package ru.yandex.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String imagePath;
    private String textPreview;
    private Long likesCount;
    private String[] tags;
    private List<CommentDto> comments;
    private List<String> textParts;
}
