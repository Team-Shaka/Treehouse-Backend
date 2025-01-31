package org.example.tree.domain.invitation.converter;

import org.example.tree.domain.invitation.dto.InvitationResponseDTO;
import org.example.tree.domain.invitation.entity.Invitation;
import org.example.tree.domain.invitation.entity.InvitationStatus;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InvitationConverter {
    public Invitation toInvitation (Profile sender, Tree tree, String phone) {
        return Invitation.builder()
                .sender(sender)
                .tree(tree)
                .phone(phone)
                .status(InvitationStatus.PENDING)
                .build();
    }

    public InvitationResponseDTO.sendInvitation toInviteUser (Invitation savedInvitation, Boolean isNewUser) {
        return InvitationResponseDTO.sendInvitation.builder()
                .availableInvitation(savedInvitation.getSender().getMember().getInvitationCount())
                .isNewUser(isNewUser)
                .build();
    }

    public InvitationResponseDTO.acceptInvitation toAcceptInvitation (Invitation invitation) {
        return InvitationResponseDTO.acceptInvitation.builder()
                .treehouseId(invitation.getTree().getId())
                .isAccepted(true)
                .build();
    }

    public InvitationResponseDTO.rejectInvitation toRejectInvitation (Invitation invitation) {
        return InvitationResponseDTO.rejectInvitation.builder()
                .treehouseId(invitation.getTree().getId())
                .isAccepted(false)
                .build();
    }

    public InvitationResponseDTO.getAvailableInvitation toGetAvailableInvitation (Member member) {
        return InvitationResponseDTO.getAvailableInvitation.builder()
                .availableInvitation(member.getInvitationCount())
                .activeRate(member.getActiveRate())
                .build();
    }

    public InvitationResponseDTO.getInvitation toGetInvitation (Invitation invitation, List<String> treeMemberProfileImages) {
        return InvitationResponseDTO.getInvitation.builder()
                .invitationId(invitation.getId())
                .treehouseName(invitation.getTree().getName())
                .senderName(invitation.getSender().getMemberName())
                .senderProfileImageUrl(invitation.getSender().getProfileImageUrl())
                .treehouseSize(invitation.getTree().getTreeSize())
                .treehouseMemberProfileImages(treeMemberProfileImages)
                .build();
    }

    public InvitationResponseDTO.getInvitations toGetInvitations(List<InvitationResponseDTO.getInvitation> invitationDtos) {
       return InvitationResponseDTO.getInvitations.builder()
               .invitations(invitationDtos)
               .build();
    }
}
