package org.example.tree.domain.invitation.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.invitation.repository.InvitationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationQueryService {
    private final InvitationRepository invitationRepository;
}
