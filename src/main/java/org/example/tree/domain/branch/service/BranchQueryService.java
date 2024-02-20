package org.example.tree.domain.branch.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.branch.entity.Branch;
import org.example.tree.domain.branch.repository.BranchRepository;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchQueryService {
    private final BranchRepository branchRepository;

    public Branch findBranch(Long treeId, Long rootId, Long leafId) {
        return branchRepository.findByTreeIdAndRootIdAndLeafId(treeId, rootId, leafId)
                .orElseThrow(() -> new GeneralException(GlobalErrorCode.BRANCH_NOT_FOUND));
    }

    public Branch findByTreeIdAndLeafId(Long treeId, Long profileId) {
        return branchRepository.findByTree_IdAndLeaf_Id(treeId, profileId)
                .orElseThrow(() -> new GeneralException(GlobalErrorCode.BRANCH_NOT_FOUND));
    }
}
