package org.example.tree.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.post.entity.Post;
import org.example.tree.domain.post.repository.PostRepository;
import org.example.tree.domain.tree.entity.Tree;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostQueryService {
    private final PostRepository postRepository;

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(GlobalErrorCode.POST_NOT_FOUND));
    }
    public List<Post> getPosts(Tree tree) {
        return postRepository.findAllByTree(tree);
    }
}
