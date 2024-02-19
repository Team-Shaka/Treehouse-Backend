package org.example.tree.domain.profile.controller;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.profile.dto.ProfileRequestDTO;
import org.example.tree.domain.profile.service.ProfileService;
import org.example.tree.domain.tree.dto.TreeRequestDTO;
import org.example.tree.global.common.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    @PostMapping(value = "/trees/members/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse registerTreeMember(
            @RequestPart ProfileRequestDTO.createProfile request,
            @RequestPart("profileImage") final MultipartFile profileImage
            ) throws Exception {
        return ApiResponse.onSuccess(profileService.createProfile(request, profileImage));
    }

    @GetMapping("/trees/{treeId}/members/{profileId}") //프로필 조회
    public ApiResponse getProfileDetails(
            @PathVariable Long treeId,
            @PathVariable Long profileId) {
        return ApiResponse.onSuccess(profileService.getProfileDetails(profileId));
    }

    @GetMapping("/trees/{treeId}/myProfile") //내 프로필 조회
    public ApiResponse getMyProfile(
            @RequestHeader("Authorization") final String header,
            @PathVariable Long treeId
    ) {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(profileService.getMyProfile(token, treeId));
    }
}
