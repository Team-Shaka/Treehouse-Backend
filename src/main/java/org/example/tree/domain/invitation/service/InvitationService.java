package org.example.tree.domain.invitation.service;


import lombok.RequiredArgsConstructor;
import org.example.tree.domain.invitation.converter.InvitationConverter;
import org.example.tree.domain.invitation.dto.InvitationRequestDTO;
import org.example.tree.domain.invitation.dto.InvitationResponseDTO;
import org.example.tree.domain.invitation.entity.Invitation;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.service.MemberQueryService;
import org.example.tree.domain.notification.service.NotificationService;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.profile.service.ProfileQueryService;
import org.example.tree.domain.tree.entity.Tree;
import org.example.tree.domain.tree.service.TreeQueryService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InvitationService {
    private final InvitationCommandService invitationCommandService;
    private final InvitationQueryService invitationQueryService;
    private final MemberQueryService memberQueryService;
    private final TreeQueryService treeQueryService;
    private final ProfileQueryService profileQueryService;
    private final NotificationService notificationService;
    private final InvitationConverter invitationConverter;

    @Transactional
    public InvitationResponseDTO.sendInvitation inviteUser(InvitationRequestDTO.sendInvitation request) {
        Member member = memberQueryService.findById(request.getSenderId());
        member.decreaseInvitationCount();
        Tree tree = treeQueryService.findById(request.getTreehouseId());
        Profile sender = profileQueryService.getTreeProfile(member, tree);
        Invitation invitation = invitationConverter.toInvitation(sender, tree, request.getPhoneNumber());
        Optional<Member> optionalMember = memberQueryService.findByPhoneNumber(request.getPhoneNumber());
        Boolean isNewUser = !optionalMember.isPresent();
        Invitation savedInvitation = invitationCommandService.createInvitation(invitation);
        return invitationConverter.toInviteUser(savedInvitation, isNewUser);
    }

    @Transactional
    public void inviteMember(InvitationRequestDTO.inviteMember request) {
        Member member = memberQueryService.findById(request.getSenderId());
        member.decreaseInvitationCount();
        Tree tree = treeQueryService.findById(request.getTreeId());
        Profile sender = profileQueryService.getTreeProfile(member, tree);
        Member targetMember = memberQueryService.findById(request.getTargetUserId());
        Invitation invitation = invitationConverter.toInvitation(sender, tree, targetMember.getPhone());
        notificationService.invitationNotification(sender, invitation, targetMember.getId());
        invitationCommandService.createInvitation(invitation);
    }

    @Transactional
    public InvitationResponseDTO.acceptInvitation acceptInvitation(InvitationRequestDTO.acceptInvitation request) {
        Invitation invitation = invitationQueryService.findById(request.getInvitationId());
        invitationCommandService.acceptInvitation(invitation);
        return invitationConverter.toAcceptInvitation(invitation);
    }

    @Transactional
    public InvitationResponseDTO.rejectInvitation rejectInvitation(InvitationRequestDTO.rejectInvitation request) {
        Invitation invitation = invitationQueryService.findById(request.getInvitationId());
        invitationCommandService.rejectInvitation(invitation);
        return invitationConverter.toRejectInvitation(invitation);
    }

    @Transactional
    public InvitationResponseDTO.getAvailableInvitation getAvailableInvitation(Member member) {
        return invitationConverter.toGetAvailableInvitation(member);
    }

    @Transactional
    public InvitationResponseDTO.getInvitations getInvitations(Member member) {
        List<Invitation> invitations = invitationQueryService.findAllByPhone(member.getPhone());
        List<InvitationResponseDTO.getInvitation> invitationDtos = invitations.stream()
                .map(invitation -> {
                    Tree tree = invitation.getTree(); // Invitation에서 Tree 정보를 가져옵니다.
                    List<Profile> treeMembers = profileQueryService.findTreeMembers(tree); // Tree의 모든 멤버를 조회합니다.

                    // treeMembers에서 무작위로 3명의 멤버를 선택합니다. (멤버 수가 3명 미만인 경우 모든 멤버를 선택)
                    List<String> randomProfileImages = treeMembers.stream()
                            .map(Profile::getProfileImageUrl)
                            .limit(3) // 최대 3명
                            .toList();

                    // toGetInvitation 메소드를 수정하여, 선택된 멤버의 profileImage 정보를 포함시켜야 합니다.
                    return invitationConverter.toGetInvitation(invitation, randomProfileImages);
                })
                .collect(Collectors.toList());

        return invitationConverter.toGetInvitations(invitationDtos);
    }
}
