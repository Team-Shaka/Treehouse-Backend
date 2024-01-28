package org.example.tree.domain.invitation.controller;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.invitation.dto.InvitationRequestDTO;
import org.example.tree.domain.invitation.dto.InvitationResponseDTO;
import org.example.tree.domain.invitation.service.InvitationService;
import org.example.tree.global.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping("/users/invitation")
    public ApiResponse<InvitationResponseDTO.sendInvitation> sendInvitation(
            @RequestBody final InvitationRequestDTO.sendInvitation request
    ) {
        return ApiResponse.onSuccess(invitationService.inviteUser(request));
    }

    @PostMapping("/trees/members/invitation")
    public ApiResponse inviteMember(
            @RequestBody final InvitationRequestDTO.inviteMember request
    ) {
        return null;
    }

    @PostMapping("/trees/members/invitation/accept")
    public ApiResponse<InvitationResponseDTO.acceptInvitation> acceptInvitation(
            @RequestBody final InvitationRequestDTO.acceptInvitation request
    ) {
        return null;
    }

    @GetMapping("/users/invitation")
    public ApiResponse<List<InvitationResponseDTO.getInvitation>> getInvitation() {
        return null;
    }

    @GetMapping("/users/availableInvitation")
    public ApiResponse<InvitationResponseDTO.getAvailableInvitation> getAvailableInvitation() {
        return null;
    }
}
