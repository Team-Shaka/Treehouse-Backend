package org.example.tree.domain.invitation.service;


import lombok.RequiredArgsConstructor;
import org.example.tree.domain.invitation.converter.InvitationConverter;
import org.example.tree.domain.invitation.dto.InvitationRequestDTO;
import org.example.tree.domain.invitation.dto.InvitationResponseDTO;
import org.example.tree.domain.invitation.entity.Invitation;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.service.MemberQueryService;
import org.example.tree.domain.tree.entity.Tree;
import org.example.tree.domain.tree.service.TreeQueryService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InvitationService {
    private final InvitationCommandService invitationCommandService;
    private final InvitationQueryService invitationQueryService;
    private final MemberQueryService memberQueryService;
    private final TreeQueryService treeQueryService;
    private final InvitationConverter invitationConverter;

    @Transactional
    public InvitationResponseDTO.sendInvitation inviteUser(InvitationRequestDTO.sendInvitation request) {
        Member sender = memberQueryService.findById(request.getSenderId());
        sender.decreaseInvitationCount();

        Tree tree = treeQueryService.findById(request.getTreeId());
        Invitation invitation = invitationConverter.toInvitation(sender, tree, request.getPhoneNumber());
        Optional<Member> optionalMember = memberQueryService.findByPhoneNumber(request.getPhoneNumber());
        Boolean isNewUser = !optionalMember.isPresent();
        Invitation savedInvitation = invitationCommandService.createInvitation(invitation);
        return invitationConverter.toInviteUser(savedInvitation, isNewUser);
    }

    @Transactional
    public void inviteMember(InvitationRequestDTO.inviteMember request) {
        Member sender = memberQueryService.findById(request.getSenderId());
        sender.decreaseInvitationCount();

        Tree tree = treeQueryService.findById(request.getTreeId());
        Member targetMember = memberQueryService.findById(request.getTargetUserId());
        Invitation invitation = invitationConverter.toInvitation(sender, tree, targetMember.getPhone());
        invitationCommandService.createInvitation(invitation);
    }

    @Transactional
    public  InvitationResponseDTO.acceptInvitation acceptInvitation(InvitationRequestDTO.acceptInvitation request) {
        Invitation invitation = invitationQueryService.findById(request.getInvitationId());
        invitationCommandService.deleteInvitation(invitation);
        return invitationConverter.toAcceptInvitation(invitation);
    }
}
