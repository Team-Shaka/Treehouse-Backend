package org.example.tree.domain.comment.repository;

import org.example.tree.domain.comment.entity.Comment;
import org.example.tree.domain.comment.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByComment(Comment comment);
}
