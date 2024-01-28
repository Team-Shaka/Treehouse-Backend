package org.example.tree.domain.invitation.converter;

import org.example.tree.domain.invitation.dto.InvitationResponseDTO;
import org.example.tree.domain.invitation.entity.Invitation;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.stereotype.Component;

@Component
public class InvitationConverter {
    public Invitation toInvitation (Member sender, Tree tree, String phone) {
        return Invitation.builder()
                .sender(sender)
                .tree(tree)
                .phone(phone)
                .build();
    }

    public InvitationResponseDTO.sendInvitation toInviteUser (Invitation savedInvitation, Boolean isNewUser) {
        return InvitationResponseDTO.sendInvitation.builder()
                .availableInvitation(savedInvitation.getSender().getInvitationCount())
                .isNewUser(isNewUser)
                .build();
    }

    public InvitationResponseDTO.acceptInvitation toAcceptInvitation (Invitation invitation) {
        return InvitationResponseDTO.acceptInvitation.builder()
                .treeId(invitation.getTree().getId())
                .isAccept(true)
                .build();
    }

    public InvitationResponseDTO.getAvailableInvitation toGetAvailableInvitation (Member member) {
        return InvitationResponseDTO.getAvailableInvitation.builder()
                .availableInvitation(member.getInvitationCount())
                .activeRate(member.getActiveRate())
                .build();
    }
}
