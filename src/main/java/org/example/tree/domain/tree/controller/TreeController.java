package org.example.tree.domain.tree.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.tree.dto.TreeRequestDTO;
import org.example.tree.domain.tree.dto.TreeResponseDTO;
import org.example.tree.domain.tree.service.TreeService;
import org.example.tree.global.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trees")
public class TreeController {
    private final TreeService treeService;

    @Operation(summary = "트리하우스 등록")
    @PostMapping("/register")
    public ApiResponse createTree(
            @RequestBody TreeRequestDTO.createTree request
    ) {
        treeService.createTree(request);
        return ApiResponse.onSuccess("");
    }

    @Operation(summary = "트리하우스 조회")
    @GetMapping
    public ApiResponse<List<TreeResponseDTO.getTree>> getTrees(
            @RequestHeader("Authorization") final String header
    )
     {
         String token = header.replace("Bearer ", "");
         return ApiResponse.onSuccess(treeService.getTrees(token));
    }

    @Operation(summary = "트리하우스 위치 변경")
    @PostMapping("/{treeId}")
    public ApiResponse<TreeResponseDTO.shiftTree> shiftTree(
            @RequestHeader("Authorization") final String header,
            @PathVariable final Long treeId
    ) {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(treeService.shiftTree(treeId, token));
    }

}
