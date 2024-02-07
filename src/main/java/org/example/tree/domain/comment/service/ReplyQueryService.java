package org.example.tree.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.comment.entity.Comment;
import org.example.tree.domain.comment.entity.Reply;
import org.example.tree.domain.comment.repository.ReplyRepository;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyQueryService {
    private final ReplyRepository replyRepository;

    public List<Reply> getReplies(Comment comment) {
        return replyRepository.findAllByComment(comment);
    }

    public Reply findById(Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(() -> new GeneralException(GlobalErrorCode.REPLY_NOT_FOUND));
    }
}
