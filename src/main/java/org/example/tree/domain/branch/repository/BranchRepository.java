package org.example.tree.domain.branch.repository;

import org.example.tree.domain.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
