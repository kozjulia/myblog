package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.CommentDto;
import ru.yandex.practicum.repository.CommentRepository;
import ru.yandex.practicum.mapper.CommentMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public void addComment(CommentDto commentDto) {
        commentRepository.save(commentMapper.toComment(commentDto));
    }

    public List<CommentDto> findAll() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toCommentDto)
                .toList();
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
