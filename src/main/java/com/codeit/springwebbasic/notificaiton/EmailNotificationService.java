package com.codeit.springwebbasic.notificaiton;

import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationService {
    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("=".repeat(50));
        System.out.println("📧 이메일 알림");
        System.out.println("수신자: " + recipient);
        System.out.println("내용: " + message);
        System.out.println("=".repeat(50));
    }
}
