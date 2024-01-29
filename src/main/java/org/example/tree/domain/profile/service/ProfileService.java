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

@Component
@RequiredArgsConstructor
public class ProfileService {
    ProfileCommandService profileCommandService;
    ProfileConverter profileConverter;
    TreeQueryService treeQueryService;
    MemberQueryService memberQueryService;

    @Transactional
    public void createProfile(ProfileRequestDTO.createProfile request) {
        Tree tree = treeQueryService.findById(request.getTreeId());
        Member member = memberQueryService.findById(request.getUserId());
        Profile newProfile = profileConverter.toProfile(tree, member, request.getMemberName(), request.getProfileImageUrl());
        profileCommandService.createProfile(newProfile);
        tree.increaseTreeSize();
    }
}
