package org.example.tree.domain.profile.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.profile.repository.ProfileRepository;
import org.example.tree.domain.tree.entity.Tree;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileQueryService {
    private final ProfileRepository profileRepository;

    public Profile getTreeProfile(Member member, Tree tree) {
        return profileRepository.findByMemberAndTree(member, tree)
                .orElseThrow(() -> new GeneralException(GlobalErrorCode.PROFILE_NOT_FOUND));
    }

    public Profile findById(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new GeneralException(GlobalErrorCode.PROFILE_NOT_FOUND));
    }

    public List<Long> findJoinedTree(Profile profile) {
        String memberId = profile.getMember().getId();
        List<Profile> foundProfiles = profileRepository.findAllByMember_Id(memberId);
        return foundProfiles.stream()
                .map(foundProfile -> foundProfile.getTree().getId())
                .toList();
    }

    public List<Profile> findTreeMembers(Tree tree) {
        return profileRepository.findAllByTree(tree);
    }

    public Profile getCurrentProfile(Member member) {
        return profileRepository.findCurrentProfile(member)
                .orElseThrow(() -> new GeneralException(GlobalErrorCode.AVAILABLE_PROFILE_NOT_FOUND));
    }
}
