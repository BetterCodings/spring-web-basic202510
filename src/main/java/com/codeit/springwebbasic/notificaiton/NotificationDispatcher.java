package com.codeit.springwebbasic.notificaiton;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationDispatcher {

    // 컬렉션으로 빈 주입받기
    private final List<NotificationService> allServices;

    public NotificationDispatcher(List<NotificationService> allServices) {
        this.allServices = allServices;
        System.out.println("등록된 알림 서비스: " + allServices.size() + "개");
    }

    public void broadcast(String recipient, String message) {
        allServices.forEach(service -> {
            service.sendNotification(recipient, message);
        });
    }
}
