package org.example.tree.domain.invitation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvitationService {
    private final InvitationCommandService invitationCommandService;
    private final InvitationQueryService invitationQueryService;
}
