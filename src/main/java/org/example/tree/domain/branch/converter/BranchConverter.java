package org.example.tree.domain.branch.converter;

import org.example.tree.domain.branch.dto.BranchResponseDTO;
import org.example.tree.domain.branch.entity.Branch;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public BranchResponseDTO.ShortestPathResult toShortestPathResult(int distance, List<Long> path) {
        return BranchResponseDTO.ShortestPathResult.builder()
                .distance(distance)
                .path(path)
                .build();
    }

    public BranchResponseDTO.NodeDTO toNodeDTO(Profile profile) {
        return BranchResponseDTO.NodeDTO.builder()
                .id(profile.getId())
                .profileImageUrl(profile.getProfileImageUrl())
                .memberName(profile.getMemberName())
                .build();
    }

    public BranchResponseDTO.LinkDTO toLinkDTO(Long rootId, Long leafId) {
        return BranchResponseDTO.LinkDTO.builder()
                .sourceId(rootId)
                .targetId(leafId)
                .build();
    }

    public BranchResponseDTO.branchView toBranchView(List<BranchResponseDTO.NodeDTO> nodes, List<BranchResponseDTO.LinkDTO> links, Long rootId, Long leafId) {
        return BranchResponseDTO.branchView.builder()
                .nodes(nodes)
                .links(links)
                .startId(rootId)
                .endId(leafId)
                .build();
    }
}
