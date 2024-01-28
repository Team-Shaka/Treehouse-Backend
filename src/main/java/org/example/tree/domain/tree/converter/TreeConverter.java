package org.example.tree.domain.tree.converter;

import org.example.tree.domain.tree.entity.Tree;
import org.springframework.stereotype.Component;

@Component
public class TreeConverter {
    public Tree toTree (String name) {
        return Tree.builder()
                .name(name)
                .build();
    }
}
