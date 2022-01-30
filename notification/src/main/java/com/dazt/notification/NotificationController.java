package com.dazt.notification;

import com.dazt.clients.notification.NotificationResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(path = "{messageNotification}")
    public NotificationResponse sendNotification(@PathVariable("messageNotification") String messageNotification) {
        Integer id = notificationService.sendNotification(messageNotification);
        log.info("Notification was sent with id: {} and message:{}", id, messageNotification);
        return new NotificationResponse(id);
    }
}
