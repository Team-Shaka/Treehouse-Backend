package org.example.tree.domain.profile.controller;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.profile.dto.ProfileRequestDTO;
import org.example.tree.domain.profile.service.ProfileService;
import org.example.tree.domain.tree.dto.TreeRequestDTO;
import org.example.tree.global.common.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    @PostMapping("/trees/members/register")
    public ApiResponse registerTreeMember(
            @RequestBody ProfileRequestDTO.createProfile request
    ) {
        profileService.createProfile(request);
        return ApiResponse.onSuccess("");
    }
}
