package org.example.tree.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.comment.entity.Comment;
import org.example.tree.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCommandService {
    private final CommentRepository commentRepository;

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
