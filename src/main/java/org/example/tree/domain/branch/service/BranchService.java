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

import java.util.*;

@Component
@RequiredArgsConstructor
public class BranchService {
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
        // 두 멤버 사이의 모든 Branch 엔티티를 찾습니다.
        List<Branch> branches = branchQueryService.findAllBranchesInTree(treeId);

        // Branch 목록을 사용하여 최단 거리를 계산합니다.
        int shortestDistance = findShortestDistance(branches, rootId, leafId);

        return shortestDistance;
    }

    public int findShortestDistance(List<Branch> branches, Long startMemberId, Long endMemberId) {
        // 멤버 ID를 기준으로 연결된 Branch를 매핑합니다.
        Map<Long, List<Long>> adjacencyList = new HashMap<>();
        for (Branch branch : branches) {
            adjacencyList.computeIfAbsent(branch.getRoot().getId(), k -> new ArrayList<>()).add(branch.getLeaf().getId());
            adjacencyList.computeIfAbsent(branch.getLeaf().getId(), k -> new ArrayList<>()).add(branch.getRoot().getId()); // 양방향 그래프
        }

        // BFS를 위한 초기화
        Queue<Long> queue = new LinkedList<>();
        Map<Long, Integer> distance = new HashMap<>(); // 각 멤버까지의 거리
        Set<Long> visited = new HashSet<>(); // 방문한 멤버 ID

        queue.offer(startMemberId);
        distance.put(startMemberId, 0);
        visited.add(startMemberId);

        // BFS 실행
        while (!queue.isEmpty()) {
            Long currentMemberId = queue.poll();
            int currentDistance = distance.get(currentMemberId);

            if (currentMemberId.equals(endMemberId)) {
                return currentDistance; // 목표 멤버에 도달했다면 거리 반환
            }

            // 인접한 멤버에 대해 탐색
            for (Long nextMemberId : adjacencyList.getOrDefault(currentMemberId, Collections.emptyList())) {
                if (!visited.contains(nextMemberId)) {
                    queue.offer(nextMemberId);
                    visited.add(nextMemberId);
                    distance.put(nextMemberId, currentDistance + 1);
                }
            }
        }

        return -1; // 경로가 없는 경우
    }
}

