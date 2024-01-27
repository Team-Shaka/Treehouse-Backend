package org.example.tree.domain.tree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TreeService {
    private final TreeCommandService treeCommandService;
}
