package org.example.tree.domain.branch.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.branch.converter.BranchConverter;
import org.example.tree.domain.branch.entity.Branch;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.profile.service.ProfileQueryService;
import org.example.tree.domain.tree.entity.Tree;
import org.example.tree.domain.tree.service.TreeQueryService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BranchService {
    private final ProfileQueryService profileQueryService;
    private final TreeQueryService treeQueryService;
    private final BranchCommandService branchCommandService;
    private final BranchQueryService branchQueryService;
    private final BranchConverter branchConverter;

    @Transactional
    public void createBranch(Long treeId,Long inviterId, Long inviteeId) {
        Tree tree = treeQueryService.findById(treeId);
        Profile inviter = profileQueryService.findById(inviterId);
        Profile invitee = profileQueryService.findById(inviteeId);
        Branch branch = branchConverter.toBranch(tree, inviter, invitee);
        branchCommandService.createBranch(branch);
    }

    @Transactional
    public Integer getBranchDegree(Long treeId, Long rootId, Long leafId) {
        Branch branch = branchQueryService.findBranch(treeId, rootId, leafId);
        return branch.getBranchDegree();
    }
}
