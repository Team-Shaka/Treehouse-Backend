package org.example.tree.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.comment.entity.Comment;
import org.example.tree.domain.comment.repository.CommentRepository;
import org.example.tree.domain.post.entity.Post;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentQueryService {
    private final CommentRepository commentRepository;

    public List<Comment> getComments(Post post) {
        return commentRepository.findAllByPost(post);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(GlobalErrorCode.COMMENT_NOT_FOUND));
    }
}
