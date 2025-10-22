package com.codeit.springwebbasic.event.listener;

import com.codeit.springwebbasic.event.BookRentedEvent;
import com.codeit.springwebbasic.notificaiton.NotificationDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationDispatcher dispatcher;

    @EventListener
    // 이벤트가 발생되면 해당 메서드가 자동으로 호출됩니다.
    // 매개값으로 이벤트 발행 시 생성한 객체가 전달 됩니다.
    public void handleBookRentedEvent(BookRentedEvent event) {
        // 알림 발송
        String message = String.format("%s님, '%s' 도서를 대여하셨습니다. 반납기한: %s"
                , event.getMember().getName()
                , event.getBook().getTitle()
                , event.getRental().getDueDate().toLocalDate()
        );

        dispatcher.broadcast(event.getMember().getName(), message);
    }
}
