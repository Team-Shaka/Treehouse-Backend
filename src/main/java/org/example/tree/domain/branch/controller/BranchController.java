package org.example.tree.domain.branch.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.branch.dto.BranchResponseDTO;
import org.example.tree.domain.branch.service.BranchService;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.global.common.CommonResponse;
import org.example.tree.global.security.handler.annotation.AuthMember;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/trees/{treeId}/branchView")
    public CommonResponse<BranchResponseDTO.branchView> getBranchView(
            @PathVariable Long treeId,
            @RequestParam("memberId") Long profileId,
            @AuthMember @Parameter(hidden = true) Member member
    ) {
        return CommonResponse.onSuccess(branchService.getBranchView(treeId, member, profileId));
    }

    @GetMapping("/trees/{treeId}/branchView/all")
    public CommonResponse<BranchResponseDTO.branchView> getCompleteBranchView(
            @PathVariable Long treeId
    ) {
        return CommonResponse.onSuccess(branchService.getCompleteBranchView(treeId));
    }
}
