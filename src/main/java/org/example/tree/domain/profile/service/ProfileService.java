package org.example.tree.domain.profile.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.branch.service.BranchService;
import org.example.tree.domain.invitation.entity.Invitation;
import org.example.tree.domain.invitation.service.InvitationQueryService;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.service.MemberQueryService;
import org.example.tree.domain.profile.converter.ProfileConverter;
import org.example.tree.domain.profile.dto.ProfileRequestDTO;
import org.example.tree.domain.profile.dto.ProfileResponseDTO;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.tree.entity.Tree;
import org.example.tree.domain.tree.service.TreeQueryService;
import org.example.tree.global.common.amazons3.S3UploadService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.example.tree.global.consts.TreeStatic.DEFAULT_PROFILE_IMAGE;

@Component
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;
    private final ProfileConverter profileConverter;
    private final TreeQueryService treeQueryService;
    private final MemberQueryService memberQueryService;
    private final S3UploadService s3UploadService;
    private final BranchService branchService;
    private final InvitationQueryService invitationQueryService;

    @Transactional
    public ProfileResponseDTO.createProfile createProfile(ProfileRequestDTO.createProfile request, MultipartFile profileImage) throws Exception {
        Tree tree = treeQueryService.findById(request.getTreeId());
        Member member = memberQueryService.findById(request.getUserId());
        boolean isNewUser = profileQueryService.isNewUser(member);
        if (!isNewUser) {
            Profile currentProfile = profileQueryService.getCurrentProfile(member);
            currentProfile.inactivate();
            profileCommandService.updateProfile(currentProfile);
        }
        String profileImageUrl = !profileImage.isEmpty() ? s3UploadService.uploadImage(profileImage) : DEFAULT_PROFILE_IMAGE;
        Profile newProfile = profileConverter.toProfile(tree, member, request.getMemberName(), request.getBio(), profileImageUrl);
        profileCommandService.createProfile(newProfile);
        tree.increaseTreeSize();
        Invitation receivedInvitation = invitationQueryService.findReceivedInvitation(member, tree);
        Profile sender = receivedInvitation.getSender();
        branchService.createBranch(tree, sender, newProfile);
        return profileConverter.toCreateProfile(newProfile);
    }

    @Transactional
    public ProfileResponseDTO.createProfile ownerProfile(ProfileRequestDTO.ownerProfile request, MultipartFile profileImage) throws Exception {
        Tree tree = treeQueryService.findById(request.getTreeId());
        Member member = memberQueryService.findById(request.getUserId());
        boolean isNewUser = profileQueryService.isNewUser(member);
        if (!isNewUser) {
            Profile currentProfile = profileQueryService.getCurrentProfile(member);
            currentProfile.inactivate();
            profileCommandService.updateProfile(currentProfile);
        }
        String profileImageUrl = !profileImage.isEmpty() ? s3UploadService.uploadImage(profileImage) : DEFAULT_PROFILE_IMAGE;
        Profile newProfile = profileConverter.toProfile(tree, member, request.getMemberName(), request.getBio(), profileImageUrl);
        profileCommandService.createProfile(newProfile);
        tree.increaseTreeSize();
        return profileConverter.toCreateProfile(newProfile);
    }

    @Transactional
    public Profile getTreeProfile(String token, Long treeId) {
        Member member = memberQueryService.findByToken(token);
        Tree tree = treeQueryService.findById(treeId);
        return profileQueryService.getTreeProfile(member,tree);
    }

    @Transactional
    public ProfileResponseDTO.getProfileDetails getProfileDetails(String token, Long profileId) {
        Member member = memberQueryService.findByToken(token);
        Profile profile = profileQueryService.findById(profileId);
        Tree tree = profile.getTree();
        Profile myProfile = profileQueryService.getTreeProfile(member,tree);
        List<Long> treeIds = profileQueryService.findJoinedTree(profile);
        int branchDegree = branchService.calculateBranchDegree(tree.getId(), myProfile.getId(), profile.getId());
        return profileConverter.toGetProfileDetails(profile, treeIds, branchDegree);
    }

    @Transactional
    public ProfileResponseDTO.getProfileDetails getMyProfile(String token, Long treeId) {
        Member member = memberQueryService.findByToken(token);
        Tree tree = treeQueryService.findById(treeId);
        Profile profile = profileQueryService.getTreeProfile(member,tree);
        List<Long> treeIds = profileQueryService.findJoinedTree(profile);
        return profileConverter.toGetProfileDetails(profile, treeIds, 0);
    }

}
