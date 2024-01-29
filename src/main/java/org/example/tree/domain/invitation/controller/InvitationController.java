package org.example.tree.domain.invitation.controller;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.invitation.dto.InvitationRequestDTO;
import org.example.tree.domain.invitation.dto.InvitationResponseDTO;
import org.example.tree.domain.invitation.service.InvitationService;
import org.example.tree.global.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

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
        invitationService.inviteMember(request);
        return ApiResponse.onSuccess("");
    }

    @PostMapping("/trees/members/invitation/accept")
    public ApiResponse<InvitationResponseDTO.acceptInvitation> acceptInvitation(
            @RequestBody final InvitationRequestDTO.acceptInvitation request
    ) {
        return ApiResponse.onSuccess(invitationService.acceptInvitation(request));
    }

    @GetMapping("/users/invitation")
    public ApiResponse<List<InvitationResponseDTO.getInvitation>> getInvitation(
            @RequestHeader("Authorization") final String header
    ) {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(invitationService.getInvitation(token));
    }

    @GetMapping("/users/availableInvitation")
    public ApiResponse<InvitationResponseDTO.getAvailableInvitation> getAvailableInvitation(
            @RequestHeader("Authorization") final String header
    ) {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(invitationService.getAvailableInvitation(token));
    }
}
