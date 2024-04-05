package org.example.tree.domain.profile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.profile.dto.ProfileRequestDTO;
import org.example.tree.domain.profile.service.ProfileService;
import org.example.tree.domain.tree.dto.TreeRequestDTO;
import org.example.tree.global.common.ApiResponse;
import org.example.tree.global.security.handler.annotation.AuthMember;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/trees/owner/register")
    @Operation(summary = "트리하우스 설립자 프로필 등록", description = "트리하우스 오너 프로필을 등록합니다.")
    public ApiResponse registerTreeOwner(
            @RequestBody ProfileRequestDTO.ownerProfile request
    ) throws Exception {
        return ApiResponse.onSuccess(profileService.ownerProfile(request));
    }

    @PostMapping("/trees/members/register")
    @Operation(summary = "트리하우스 멤버 프로필 등록", description = "트리하우스 멤버 프로필을 등록합니다.")
    public ApiResponse registerTreeMember(
            @RequestBody ProfileRequestDTO.createProfile request
            ) throws Exception {
        return ApiResponse.onSuccess(profileService.createProfile(request));
    }

    @GetMapping("/trees/{treeId}/members/{profileId}") //프로필 조회
    @Operation(summary = "멤버 프로필 조회", description = "트리하우스 속 특정 멤버의 프로필을 조회합니다.")
    public ApiResponse getProfileDetails(
            @AuthMember @Parameter(hidden = true) Member member,
            @PathVariable Long treeId,
            @PathVariable Long profileId) {
        return ApiResponse.onSuccess(profileService.getProfileDetails(member, profileId));
    }

    @GetMapping("/trees/{treeId}/myProfile") //내 프로필 조회
    @Operation(summary = "내 프로필 조회", description = "트리하우스 속 내 프로필을 조회합니다.")
    public ApiResponse getMyProfile(
            @AuthMember @Parameter(hidden = true) Member member,
            @PathVariable Long treeId
    ) {
        return ApiResponse.onSuccess(profileService.getMyProfile(member, treeId));
    }
}
