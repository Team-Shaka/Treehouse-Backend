package org.example.tree.domain.branch.converter;

import org.example.tree.domain.branch.entity.Branch;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.stereotype.Component;

@Component
public class BranchConverter {

    public Branch toBranch(Tree tree, Profile inviter, Profile invitee) {
        return Branch.builder()
                .tree(tree)
                .root(inviter)
                .leaf(invitee)
                .branchDegree(1)
                .build();
    }
}
