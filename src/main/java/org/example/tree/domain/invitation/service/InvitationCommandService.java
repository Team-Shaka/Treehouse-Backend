package org.example.tree.domain.invitation.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.invitation.entity.Invitation;
import org.example.tree.domain.invitation.repository.InvitationRepository;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationCommandService {
    private final InvitationRepository invitationRepository;

    public Invitation createInvitation(Invitation invitation) {
        return invitationRepository.save(invitation);
    }
}
