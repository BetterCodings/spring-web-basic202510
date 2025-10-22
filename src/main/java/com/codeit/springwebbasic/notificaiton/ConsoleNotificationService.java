package com.codeit.springwebbasic.notificaiton;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary // 기본 구현체
public class ConsoleNotificationService implements NotificationService {
    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("=".repeat(50));
        System.out.println("📢 콘솔 알림");
        System.out.println("수신자: " + recipient);
        System.out.println("내용: " + message);
        System.out.println("=".repeat(50));
    }
}
