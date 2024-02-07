package org.example.tree.domain.post.repository;

import org.example.tree.domain.post.entity.Post;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTree(Tree tree);
}
