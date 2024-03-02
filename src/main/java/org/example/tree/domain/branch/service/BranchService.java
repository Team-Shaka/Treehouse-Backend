package org.example.tree.domain.branch.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.branch.converter.BranchConverter;
import org.example.tree.domain.branch.dto.BranchResponseDTO;
import org.example.tree.domain.branch.entity.Branch;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.service.MemberQueryService;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.profile.service.ProfileQueryService;
import org.example.tree.domain.tree.entity.Tree;
import org.example.tree.domain.tree.service.TreeQueryService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BranchService {
    private final BranchCommandService branchCommandService;
    private final BranchQueryService branchQueryService;
    private final BranchConverter branchConverter;
    private final ProfileQueryService profileQueryService;
    private final MemberQueryService memberQueryService;
    private final TreeQueryService treeQueryService;

    @Transactional
    public void createBranch(Tree tree,Profile inviter, Profile invitee) {
        Branch branch = branchConverter.toBranch(tree, inviter, invitee);
        branchCommandService.createBranch(branch);
    }

//    @Transactional
//    public Integer getBranchDegree(Long treeId, Long rootId, Long leafId) {
//        Branch branch = branchQueryService.findBranch(treeId, rootId, leafId);
//        return branch.getBranchDegree();
//    }

    public int calculateBranchDegree(Long treeId, Long rootId, Long leafId) {
        // 두 멤버 사이의 모든 Branch 엔티티를 찾습니다.
        List<Branch> branches = branchQueryService.findAllBranchesInTree(treeId);

        // Branch 목록을 사용하여 최단 거리를 계산합니다.
        int shortestDistance = findShortestDistance(branches, rootId, leafId).getDistance();

        return shortestDistance;
    }

    public BranchResponseDTO.ShortestPathResult findShortestDistance(List<Branch> branches, Long startMemberId, Long endMemberId) {
        Map<Long, List<Long>> adjacencyList = new HashMap<>();
        Map<Long, Long> prev = new HashMap<>();
        Set<Long> visited = new HashSet<>();
        Queue<Long> queue = new LinkedList<>();

        // 각 멤버 ID를 기준으로 연결된 Branch를 매핑
        for (Branch branch : branches) {
            adjacencyList.computeIfAbsent(branch.getRoot().getId(), k -> new ArrayList<>()).add(branch.getLeaf().getId());
            adjacencyList.computeIfAbsent(branch.getLeaf().getId(), k -> new ArrayList<>()).add(branch.getRoot().getId());
        }

        queue.add(startMemberId);
        visited.add(startMemberId);
        prev.put(startMemberId, null); // 시작 노드의 선행자는 없음

        while (!queue.isEmpty()) {
            Long current = queue.poll();
            if (current.equals(endMemberId)) {
                break; // 목표 노드에 도달
            }
            for (Long neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    prev.put(neighbor, current);
                }
            }
        }

        // 경로 복원 및 결과 생성
        List<Long> path = new ArrayList<>();
        Long current = endMemberId;
        while (current != null) {
            path.add(current);
            current = prev.get(current);
        }
        Collections.reverse(path); // 경로를 역순으로 뒤집어 정상 순서로 만듦

        int distance = path.size() - 1; // 거리는 경로의 길이에서 1을 뺀 값
        return branchConverter.toShortestPathResult(distance, path);
    }
    public BranchResponseDTO.branchView getBranchView(Long treeId, String token, Long leafId) {
        Member member = memberQueryService.findByToken(token);
        Tree tree = treeQueryService.findById(treeId);
        List<Branch> branches = branchQueryService.findAllBranchesInTree(treeId);
        Long rootId = profileQueryService.getTreeProfile(member, tree).getId();
        BranchResponseDTO.ShortestPathResult result = findShortestDistance(branches, rootId, leafId);

        // Node 정보 생성
        List<BranchResponseDTO.NodeDTO> nodes = result.getPath().stream()
                .map(profileId -> profileQueryService.findById(profileId))
                .map(branchConverter::toNodeDTO)
                .collect(Collectors.toList());

        // Link 정보 생성
        List<BranchResponseDTO.LinkDTO> links = new ArrayList<>();
        for (int i = 0; i < result.getPath().size() - 1; i++) {
            links.add(branchConverter.toLinkDTO(result.getPath().get(i), result.getPath().get(i + 1)));
        }

        // 최종 DTO 생성 및 반환
        return branchConverter.toBranchView(nodes, links, rootId, leafId);
    }
}

