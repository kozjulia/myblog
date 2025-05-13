package ru.yandex.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Builder
@Table("posts")
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    private Long id;
    private String title;
    private String imagePath;
    private String text;
    private Long likesCount;
    private String[] tags;
    @MappedCollection(idColumn = "post_id")
    private List<Comment> comments;
}