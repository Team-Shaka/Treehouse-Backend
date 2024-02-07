package org.example.tree.domain.profile.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.service.MemberQueryService;
import org.example.tree.domain.profile.converter.ProfileConverter;
import org.example.tree.domain.profile.dto.ProfileRequestDTO;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.tree.entity.Tree;
import org.example.tree.domain.tree.service.TreeQueryService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;
    private final ProfileConverter profileConverter;
    private final TreeQueryService treeQueryService;
    private final MemberQueryService memberQueryService;

    @Transactional
    public void createProfile(ProfileRequestDTO.createProfile request) {
        Tree tree = treeQueryService.findById(request.getTreeId());
        Member member = memberQueryService.findById(request.getUserId());
        Profile newProfile = profileConverter.toProfile(tree, member, request.getMemberName(), request.getProfileImageUrl());
        profileCommandService.createProfile(newProfile);
        System.out.println("newProfile = " + newProfile);
        System.out.println("newProfile.getMemberName() = " + newProfile.getMemberName());
        System.out.println("newProfile.getProfileImageUrl() = " + newProfile.getProfileImageUrl());
        tree.increaseTreeSize();
    }

    @Transactional
    public Profile getTreeProfile(String token, Long treeId) {
        Member member = memberQueryService.findByToken(token);
        Tree tree = treeQueryService.findById(treeId);
        return profileQueryService.getTreeProfile(member,tree);
    }
}
