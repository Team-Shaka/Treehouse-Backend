package org.example.tree.domain.tree.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.service.MemberQueryService;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.profile.service.ProfileQueryService;
import org.example.tree.domain.tree.converter.TreeConverter;
import org.example.tree.domain.tree.dto.TreeRequestDTO;
import org.example.tree.domain.tree.dto.TreeResponseDTO;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TreeService {
    private final TreeCommandService treeCommandService;
    private final TreeQueryService treeQueryService;
    private final TreeConverter treeConverter;
    private final MemberQueryService memberQueryService;
    private final ProfileQueryService profileQueryService;

    @Transactional
    public void createTree(TreeRequestDTO.createTree request) {
        Tree tree = treeConverter.toTree(request.getTreeName());
        treeCommandService.createTree(tree);
    }

    @Transactional
    public List<TreeResponseDTO.getTree> getTrees(String token) {
        Member member = memberQueryService.findByToken(token);
        Profile currentProfile = profileQueryService.getCurrentProfile(member);
        List<Long> treeIds = profileQueryService.findJoinedTree(currentProfile);
        List<Tree> trees = treeIds.stream()
                .map(treeQueryService::findById)
                .collect(Collectors.toList());
        return  trees.stream()
                .map(tree -> {
                    List<Profile> treeMembers = profileQueryService.findTreeMembers(tree); // Tree의 모든 멤버를 조회합니다.
                    List<String> randomProfileImages = treeMembers.stream()
                            .map(Profile::getProfileImageUrl)
                            .limit(3) // 최대 3명
                            .collect(Collectors.toList());
                    return treeConverter.toGetTree(tree, randomProfileImages, currentProfile);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public TreeResponseDTO.shiftTree shiftTree(Long treeId, String token) {
        Member member = memberQueryService.findByToken(token);
        Tree tree = treeQueryService.findById(treeId);
        Profile currentProfile = profileQueryService.getCurrentProfile(member);
        currentProfile.inactivate();
        Profile shiftedProfile = profileQueryService.getTreeProfile(member, tree);
        shiftedProfile.actvate();
        return treeConverter.toShiftTree(tree);

    }
}
