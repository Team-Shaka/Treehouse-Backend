package org.example.tree.domain.invitation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.invitation.dto.InvitationRequestDTO;
import org.example.tree.domain.invitation.dto.InvitationResponseDTO;
import org.example.tree.domain.invitation.service.InvitationService;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.global.common.CommonResponse;
import org.example.tree.global.security.handler.annotation.AuthMember;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping("/users/invitation")
    @Operation(summary = "신규회원 초대", description = "서비스에 미가입된 사용자를 초대합니다.")
    public CommonResponse<InvitationResponseDTO.sendInvitation> sendInvitation(
            @RequestBody final InvitationRequestDTO.sendInvitation request
    ) {
        return CommonResponse.onSuccess(invitationService.inviteUser(request));
    }

    @PostMapping("/trees/members/invitation")
    @Operation(summary = "회원 초대", description = "서비스에 가입되어 있는 멤버를 다른 트리하우스에 초대합니다.")
    public CommonResponse inviteMember(
            @RequestBody final InvitationRequestDTO.inviteMember request
    ) {
        invitationService.inviteMember(request);
        return CommonResponse.onSuccess("");
    }

    @PostMapping("/treehouses/members/invitation/accept")
    @Operation(summary = "초대 수락", description = "받은 초대를 수락합니다.")
    public CommonResponse<InvitationResponseDTO.acceptInvitation> acceptInvitation(
            @RequestBody final InvitationRequestDTO.acceptInvitation request
    ) {
        return CommonResponse.onSuccess(invitationService.acceptInvitation(request));
    }

    @PostMapping("/treehouses/members/invitation/reject")
    @Operation(summary = "초대 거절", description = "받은 초대를 거절합니다.")
    public CommonResponse<InvitationResponseDTO.rejectInvitation> rejectInvitation(
            @RequestBody final InvitationRequestDTO.rejectInvitation request
    ) {
        return CommonResponse.onSuccess(invitationService.rejectInvitation(request));
    }

    @GetMapping("/users/invitation")
    @Operation(summary = "초대장 조회", description = "내가 받은 초대장을 조회합니다.")
    public CommonResponse<InvitationResponseDTO.getInvitations> getInvitations(
            @AuthMember @Parameter(hidden = true) Member member
    ) {
        return CommonResponse.onSuccess(invitationService.getInvitations(member));
    }

    @GetMapping("/users/availableInvitation")
    @Operation(summary = "가용 초대장 조회", description = "내가 보낼 수 있는 초대장 개수를 조회합니다.")
    public CommonResponse<InvitationResponseDTO.getAvailableInvitation> getAvailableInvitation(
            @AuthMember @Parameter(hidden = true)  Member member
    ) {
        return CommonResponse.onSuccess(invitationService.getAvailableInvitation(member));
    }
}
