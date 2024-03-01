package org.example.tree.domain.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.notification.dto.NotificationResponseDTO;
import org.example.tree.domain.notification.service.NotificationService;
import org.example.tree.global.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 생성 테스트")
    @PostMapping("/test")
    public ApiResponse<NotificationResponseDTO.sendNotification> sendNotification(
            @RequestBody final NotificationRequestDTO.sendNotification request)
     {
        return ApiResponse.onSuccess(notificationService.sendNotification(request));
    }

    @Operation(summary = "전체 알림 조회", description = "유저가 받은 알림들을 조회합니다.")
    @GetMapping
    public ApiResponse<List<NotificationResponseDTO.getNotification>> getNotifications(
            @RequestHeader("Authorization") final String header)
    {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(notificationService.getUserNotifications(token));
    }

    @Operation(summary = "특정 알림 조회", description = "유저가 받은 알림 중 하나를 조회합니다.")
    @GetMapping("/{notificationId}")
    public ApiResponse<NotificationResponseDTO.getNotification> getNotification(
            @PathVariable final Long notificationId)
    {
        return ApiResponse.onSuccess(notificationService.getNotification(notificationId));
    }

}
