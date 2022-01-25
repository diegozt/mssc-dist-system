package com.dazt.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "NOTIFICATION"
)
public interface NotificationClient {

    @GetMapping(path = "api/v1/notification/{message}")
    NotificationResponse sendNotification(@PathVariable("message") String message);

}
