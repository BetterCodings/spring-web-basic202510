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

    public Book(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.author = builder.author;
        this.isbn = builder.isbn;
        this.publisher = builder.publisher;
        this.publishedDate = builder.publishedDate;
        this.status = builder.status;
    }

    // 빌더 패턴 구현 - 객체를 생성하고 초기화 할 때 생성자나 setter 대체하기 위해 사용.
    public static class Builder {
        // 원본 클래스와 완벽하게 동일한 필드를 구성
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private String publisher;
        private LocalDate publishedDate;
        private BookStatus status;

        public Builder() {}

        // 필드를 초기화하는 setter를 자기 필드명과 동일하게 생성
        public Builder id(Long id) {
            // 자기 자신 객체를 리턴
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder publishedDate(LocalDate publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        public Builder status(BookStatus status) {
            this.status = status;
            return this;
        }

        // 최종 연산에서는 원본 객체를 리턴
        public Book build() {
            return new Book(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

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
