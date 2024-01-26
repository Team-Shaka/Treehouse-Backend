package org.example.tree.domain.invitation.controller;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.invitation.service.InvitationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;
}
