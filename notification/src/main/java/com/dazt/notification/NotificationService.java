package com.dazt.notification;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Integer sendNotification(String notificationMessage) {
        Notification notification = notificationRepository.saveAndFlush(
                Notification.builder()
                        .message(notificationMessage)
                        .build()
        );
        return notification.getId();
    }
}
