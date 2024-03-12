package org.example.tree.global.common.fcm.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.global.common.fcm.dto.FCMRequestDTO;
import org.example.tree.global.common.fcm.entity.FCMDevice;
import org.example.tree.global.common.fcm.repository.FCMDeviceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FCMDeviceService {

    FCMDeviceRepository fcmDeviceRepository;

    public Long saveDevice(FCMRequestDTO.saveDevice request) {
        FCMDevice fcmDevice = FCMDevice.builder()
                .deviceUuid(request.getDeviceUuid())
                .fcmToken(request.getFcmToken())
                .userId(request.getUserId())
                .build();
        return fcmDeviceRepository.save(fcmDevice).getId();
    }
}
