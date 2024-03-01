package org.example.tree.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.post.entity.PostImage;
import org.example.tree.domain.post.repository.PostImageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostImageCommandService {
    private final PostImageRepository postImageRepository;

    public void createPostImage(final PostImage postImage) {
        postImageRepository.save(postImage);
    }
}
