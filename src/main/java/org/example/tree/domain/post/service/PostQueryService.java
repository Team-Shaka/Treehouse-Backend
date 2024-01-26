package org.example.tree.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostQueryService {
    private final PostRepository postRepository;
}
