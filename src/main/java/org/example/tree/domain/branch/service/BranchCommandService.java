package org.example.tree.domain.branch.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.branch.entity.Branch;
import org.example.tree.domain.branch.repository.BranchRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchCommandService {
    private final BranchRepository branchRepository;

    public void createBranch(Branch branch) {
        branchRepository.save(branch);
    }
}
