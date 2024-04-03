package org.example.tree.domain.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.notification.dto.NotificationRequestDTO;
import org.example.tree.domain.notification.dto.NotificationResponseDTO;
import org.example.tree.domain.notification.service.NotificationService;
import org.example.tree.global.common.ApiResponse;
import org.example.tree.global.security.handler.annotation.AuthMember;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 생성 테스트")
    @PostMapping("/test")
    public ApiResponse sendNotification(
            @RequestBody final NotificationRequestDTO.sendNotification request)
     {
         notificationService.sendNotification(request.getTitle(), request.getMessage(), request.getType(), request.getReceiverId());
        return ApiResponse.onSuccess("success");
    }

    @Operation(summary = "전체 알림 조회", description = "유저가 받은 알림들을 조회합니다.")
    @GetMapping
    public ApiResponse<List<NotificationResponseDTO.getNotification>> getNotifications(
            @AuthMember @Parameter(hidden = true) Member member
    ) {
        return ApiResponse.onSuccess(notificationService.getUserNotifications(member));
    }

    @Operation(summary = "특정 알림 조회", description = "유저가 받은 알림 중 하나를 조회합니다.")
    @GetMapping("/{notificationId}")
    public ApiResponse<NotificationResponseDTO.getNotification> getNotification(
            @PathVariable final Long notificationId)
    {
        return ApiResponse.onSuccess(notificationService.getNotification(notificationId));
    }

}
