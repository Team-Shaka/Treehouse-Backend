package org.example.tree.domain.notification.repository;

import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.receiver = :member AND n.readStatus = false")
    List<Notification> findAllUnreadByReceiver(@Param("member") Member member);


    @Query("SELECT n FROM Notification n WHERE n.id = :id AND n.readStatus = false")
    Optional<Notification> findUnreadById(@Param("id") Long notificationId);
}
