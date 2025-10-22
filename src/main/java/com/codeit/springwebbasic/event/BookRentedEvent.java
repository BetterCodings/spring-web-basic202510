package com.codeit.springwebbasic.event;

import com.codeit.springwebbasic.book.entity.Book;
import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.rental.entity.Rental;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BookRentedEvent extends ApplicationEvent {

    private final Rental rental;
    private final Member member;
    private final Book book;

    public BookRentedEvent(Object source, Rental rental) {
        super(source);
        this.rental = rental;
        this.member = rental.getMember();
        this.book = rental.getBook();
    }
}
