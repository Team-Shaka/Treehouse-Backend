package org.example.tree.domain.tree.converter;

import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.tree.dto.TreeResponseDTO;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TreeConverter {
    public Tree toTree (String name) {
        return Tree.builder()
                .name(name)
                .build();
    }

    public TreeResponseDTO.getTree toGetTree(Tree tree, List<String> treeMemberProfileImages, Profile currentProfile) {
        Boolean isSelected = currentProfile.getTree().getId().equals(tree.getId());
        return TreeResponseDTO.getTree.builder()
                .treeName(tree.getName())
                .treeSize(tree.getTreeSize())
                .treeMemberProfileImages(treeMemberProfileImages)
                .isSelected(isSelected)
                .build();
    }
}
