package org.example.tree.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostService {
    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;
}
