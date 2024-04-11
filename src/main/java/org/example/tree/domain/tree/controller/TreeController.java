package org.example.tree.domain.tree.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.tree.dto.TreeRequestDTO;
import org.example.tree.domain.tree.dto.TreeResponseDTO;
import org.example.tree.domain.tree.service.TreeService;
import org.example.tree.global.common.CommonResponse;
import org.example.tree.global.security.handler.annotation.AuthMember;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trees")
public class TreeController {
    private final TreeService treeService;

    @Operation(summary = "트리하우스 등록")
    @PostMapping("/register")
    public CommonResponse createTree(
            @RequestBody TreeRequestDTO.createTree request
    ) {
        treeService.createTree(request);
        return CommonResponse.onSuccess("");
    }

    @Operation(summary = "트리하우스 조회")
    @GetMapping
    public CommonResponse<List<TreeResponseDTO.getTree>> getTrees(
            @AuthMember @Parameter(hidden = true) Member member
    ) {
         return CommonResponse.onSuccess(treeService.getTrees(member));
    }

    @Operation(summary = "트리하우스 위치 변경")
    @PostMapping("/{treeId}")
    public CommonResponse<TreeResponseDTO.shiftTree> shiftTree(
            @AuthMember @Parameter(hidden = true) Member member,
            @PathVariable final Long treeId
    ) {
        return CommonResponse.onSuccess(treeService.shiftTree(treeId, member));
    }

}
