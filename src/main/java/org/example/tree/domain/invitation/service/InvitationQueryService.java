package org.example.tree.domain.invitation.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.invitation.entity.Invitation;
import org.example.tree.domain.invitation.repository.InvitationRepository;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.tree.entity.Tree;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationQueryService {
    private final InvitationRepository invitationRepository;

    public Invitation findById(Long id) {
        return invitationRepository.findById(id).orElseThrow(() -> new GeneralException(GlobalErrorCode.INVITATION_NOT_FOUND));
    }

    public List<Invitation> findAllByPhone(String phone) {
        return invitationRepository.findAllByPhone(phone);
    }

    public Invitation findReceivedInvitation(Member member, Tree tree) {
        return invitationRepository.findByPhoneAndTree(member.getPhone(), tree)
                .orElseThrow(() -> new GeneralException(GlobalErrorCode.INVITATION_NOT_FOUND));
    }
}
