package org.example.tree.global.common.fcm.repository;

import org.example.tree.global.common.fcm.entity.FCMDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FCMDeviceRepository extends JpaRepository<FCMDevice, Long> {
    FCMDevice findByDeviceUuid(String deviceUuid);
}
