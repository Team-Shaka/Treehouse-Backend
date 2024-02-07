package org.example.tree.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.comment.entity.Reply;
import org.example.tree.domain.comment.repository.ReplyRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyCommandService {

    private final ReplyRepository replyRepository;

    public void createReply(Reply reply) {
        replyRepository.save(reply);
    }

    public void deleteReply(Reply reply) {
        replyRepository.delete(reply);
    }
}
