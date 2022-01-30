package com.dazt.notification.rabbitmq;

import com.dazt.notification.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    public void consumer(String notificationMessage){
        log.info("Consumed {} from queue", notificationMessage);
        notificationService.sendNotification(notificationMessage);
    }

}
