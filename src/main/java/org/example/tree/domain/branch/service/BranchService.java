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
    public void createBranch(Tree tree,Profile inviter, Profile invitee) {
        Branch branch = branchConverter.toBranch(tree, inviter, invitee);
        branchCommandService.createBranch(branch);
    }

    @Transactional
    public Integer getBranchDegree(Long treeId, Long rootId, Long leafId) {
        Branch branch = branchQueryService.findBranch(treeId, rootId, leafId);
        return branch.getBranchDegree();
    }

    public int calculateBranchDegree(Long treeId, Long rootId, Long leafId) {
        int degree = 1;
        Long currentMemberId = leafId;

        while (true) {
            // 현재 멤버를 초대한 멤버를 찾습니다.
            Branch branch = branchQueryService.findByTreeIdAndLeafId(treeId, currentMemberId);
            Long inviterId = branch.getRoot().getId();
            // 루트 사용자에 도달했거나, 더 이상 상위 사용자가 없는 경우 루프를 종료합니다.
            if ((branch == null) || (inviterId.equals(rootId))) {
                break;
            }

            // BranchDegree를 증가시키고, 다음 상위 사용자로 이동합니다.
            degree++;
            currentMemberId = inviterId;
        }
        System.out.println("degree = " + degree);
        return degree;
    }
}

