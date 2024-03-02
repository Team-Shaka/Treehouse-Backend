package org.example.tree.domain.branch.controller;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.branch.dto.BranchResponseDTO;
import org.example.tree.domain.branch.service.BranchService;
import org.example.tree.global.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/trees/{treeId}/branchView")
    public ApiResponse<BranchResponseDTO.branchView> getBranchView(
            @PathVariable Long treeId,
            @RequestParam("memberId") Long profileId,
            @RequestHeader("Authorization") final String header
    ) {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(branchService.getBranchView(treeId, token, profileId));
    }

    @GetMapping("/trees/{treeId}/branchView/all")
    public ApiResponse<BranchResponseDTO.branchView> getCompleteBranchView(
            @PathVariable Long treeId
    ) {
        return ApiResponse.onSuccess(branchService.getCompleteBranchView(treeId));
    }
}
