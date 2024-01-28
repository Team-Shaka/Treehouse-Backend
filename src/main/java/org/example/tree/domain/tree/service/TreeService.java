package org.example.tree.domain.tree.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.tree.converter.TreeConverter;
import org.example.tree.domain.tree.dto.TreeRequestDTO;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TreeService {
    private final TreeCommandService treeCommandService;
    private final TreeQueryService treeQueryService;
    private final TreeConverter treeConverter;

    @Transactional
    public void createTree(TreeRequestDTO.createTree request) {
        Tree tree = treeConverter.toTree(request.getTreeName());
        treeCommandService.createTree(tree);
    }
}
