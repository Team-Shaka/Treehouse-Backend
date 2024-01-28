package org.example.tree.domain.tree.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.tree.entity.Tree;
import org.example.tree.domain.tree.repository.TreeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TreeCommandService {
    private final TreeRepository treeRepository;

    public void createTree(Tree tree) {
        treeRepository.save(tree);
    }
}
