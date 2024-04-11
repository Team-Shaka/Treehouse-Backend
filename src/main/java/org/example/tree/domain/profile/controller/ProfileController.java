package org.example.tree.domain.profile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.profile.dto.ProfileRequestDTO;
import org.example.tree.domain.profile.service.ProfileService;
import org.example.tree.global.common.CommonResponse;
import org.example.tree.global.security.handler.annotation.AuthMember;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/treehouses/owner/register")
    @Operation(summary = "트리하우스 설립자 등록", description = "트리하우스의 설립자(Owner)로 등록합니다.")
    public CommonResponse registerTreehouseOwner(
            @RequestBody ProfileRequestDTO.ownerProfile request,
            @AuthMember @Parameter(hidden = true) Member member
    ) throws Exception {
        return CommonResponse.onSuccess(profileService.ownerProfile(request, member));
    }

    @PostMapping("/treehouses/members/register")
    @Operation(summary = "트리하우스 멤버 가입", description = "트리하우스의 멤버로 등록합니다.")
    public CommonResponse registerTreehouseMember(
            @RequestBody ProfileRequestDTO.createProfile request,
            @AuthMember @Parameter(hidden = true) Member member
            ) throws Exception {
        return CommonResponse.onSuccess(profileService.createProfile(request, member));
    }

    @GetMapping("/treehouses/{treeId}/members/{profileId}") //프로필 조회
    @Operation(summary = "멤버 프로필 조회", description = "트리하우스 속 특정 멤버의 프로필을 조회합니다.")
    public CommonResponse getProfileDetails(
            @AuthMember @Parameter(hidden = true) Member member,
            @PathVariable Long treeId,
            @PathVariable Long profileId) {
        return CommonResponse.onSuccess(profileService.getProfileDetails(member, profileId));
    }

    @GetMapping("/treehouses/{treeId}/myProfile") //내 프로필 조회
    @Operation(summary = "내 프로필 조회", description = "트리하우스 속 내 프로필을 조회합니다.")
    public CommonResponse getMyProfile(
            @AuthMember @Parameter(hidden = true) Member member,
            @PathVariable Long treeId
    ) {
        return CommonResponse.onSuccess(profileService.getMyProfile(member, treeId));
    }
}
