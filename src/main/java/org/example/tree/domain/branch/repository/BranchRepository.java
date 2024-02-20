package org.example.tree.domain.branch.repository;

import org.example.tree.domain.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    @Query("SELECT b FROM Branch b " +
            "WHERE b.tree.id = :treeId " +
            "AND b.root.id = :rootId " +
            "AND b.leaf.id = :leafId")
    Optional<Branch> findByTreeIdAndRootIdAndLeafId(@Param("treeId") Long treeId,
                                                    @Param("rootId") Long rootId,
                                                    @Param("leafId") Long leafId);

    Optional<Branch> findByTree_IdAndLeaf_Id(Long treeId, Long profileId);
}
