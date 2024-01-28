package org.example.tree.domain.tree.controller;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.tree.dto.TreeRequestDTO;
import org.example.tree.domain.tree.dto.TreeResponseDTO;
import org.example.tree.domain.tree.service.TreeService;
import org.example.tree.global.common.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trees")
public class TreeController {
    private final TreeService treeService;

    @PostMapping("/register")
    public ApiResponse createTree(
            @RequestBody TreeRequestDTO.createTree request
    ) {
        treeService.createTree(request);
        return ApiResponse.onSuccess("");
    }

    @PostMapping("/members/register")
    public ApiResponse registerTreeMember(
            @RequestBody TreeRequestDTO.registerTreeMember request
    ) {
        return null;
    }
}
