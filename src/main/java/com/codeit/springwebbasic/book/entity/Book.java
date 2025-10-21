package com.codeit.springwebbasic.book.entity;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private LocalDate publishedDate;
    private BookStatus status;

    // 나중에 DB를 사용하면 status 값도 DB에 저장될 것이기 때문에
    // 밑에 2개의 메서드는 사라지게 될 것이다

    public void rentOut() {
        if (this.status != BookStatus.AVAILABLE) {
            throw new IllegalArgumentException("이미 대여중인 도서입니다.");
        }
        this.status = BookStatus.RENTED;
    }

    public void returnBook() {
        if (this.status != BookStatus.RENTED) {
            throw new IllegalArgumentException("대여중이 아닌 도서입니다.");
        }
        this.status = BookStatus.AVAILABLE;
    }

}
