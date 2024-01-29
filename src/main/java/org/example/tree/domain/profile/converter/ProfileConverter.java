package org.example.tree.domain.profile.converter;

import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.profile.dto.ProfileResponseDTO;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverter {
    public Profile toProfile (Tree tree, Member member, String memberName, String profileImageUrl) {
        return Profile.builder()
                .tree(tree)
                .member(member)
                .memberName(memberName)
                .profileImageUrl(profileImageUrl)
                .build();
    }

}
