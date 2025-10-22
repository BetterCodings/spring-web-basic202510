package com.codeit.springwebbasic.rental.service;

import com.codeit.springwebbasic.book.entity.Book;
import com.codeit.springwebbasic.book.repository.BookRepository;
import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.member.repository.MemberRepository;
import com.codeit.springwebbasic.notificaiton.NotificationService;
import com.codeit.springwebbasic.rental.entity.Rental;
import com.codeit.springwebbasic.rental.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final NotificationService notificationService; // @Primary로 Console 주입

    public Rental rentBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 회원이 없습니다."));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 책이 없습니다."));

        // 대여 처리
        book.rentOut();

        Rental rental = Rental.builder()
                .member(member)
                .book(book)
                .rentedAt(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(7))
                .build();

        Rental saved = rentalRepository.save(rental);

        // 알림 발송
        String message = String.format("%s님, '%s' 도서를 대여하셨습니다. 반납기한: %s"
                , member.getName(), book.getTitle(), rental.getDueDate().toLocalDate()
        );
        notificationService.sendNotification(member.getName(), message);

        return saved;
    }
}
