package org.example.tree.domain.profile.converter;

import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.profile.dto.ProfileResponseDTO;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProfileConverter {
    public Profile toProfile (Tree tree, Member member, String memberName, String bio, String profileImageUrl) {
        return Profile.builder()
                .tree(tree)
                .member(member)
                .memberName(memberName)
                .bio(bio)
                .profileImageUrl(profileImageUrl)
                .build();
    }

    public ProfileResponseDTO.createProfile toCreateProfile(Profile profile) {
        return ProfileResponseDTO.createProfile.builder()
                .profileId(profile.getId())
                .treeId(profile.getTree().getId())
                .memberName(profile.getMemberName())
                .profileImageUrl(profile.getProfileImageUrl())
                .build();
    }

}
