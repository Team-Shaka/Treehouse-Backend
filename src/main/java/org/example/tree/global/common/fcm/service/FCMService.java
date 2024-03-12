package org.example.tree.global.common.fcm.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tree.global.common.fcm.entity.FCMDevice;
import org.example.tree.global.common.fcm.repository.FCMDeviceRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMService {

    private final FCMDeviceRepository fcmDeviceRepository;
    public static final String PUSH_CLICK = "push_click";

    public String send(String deviceUuid, String title, String body) {
        FCMDevice targetUser = fcmDeviceRepository.findByDeviceUuid(deviceUuid);

        Message message = buildFcmMessage(title, body, targetUser.getFcmToken());
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM-send-" + response);
        } catch (FirebaseMessagingException e) {
            log.info("FCM-except-" + e.getMessage());
            return "알림 실패";
        }
        return "알림 전송";
    }

    private Message buildFcmMessage(String title, String body, String token) {
        return Message.builder()
                .setToken(token)
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build()
                )
                .setAndroidConfig(buildAndroidConfig(title, body))
                .setApnsConfig(buildApnsConfig())
                .build();
    }

    private AndroidConfig buildAndroidConfig(String title, String body) {
        return AndroidConfig.builder()
                .setNotification(
                        AndroidNotification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .setClickAction(PUSH_CLICK)
                                .build()
                )
                .build();
    }

    private ApnsConfig buildApnsConfig() {
        return ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setCategory(PUSH_CLICK)
                        .build())
                .build();
    }
}
