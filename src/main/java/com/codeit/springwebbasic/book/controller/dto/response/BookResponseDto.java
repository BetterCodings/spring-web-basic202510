package com.codeit.springwebbasic.book.controller.dto.response;

import com.codeit.springwebbasic.book.entity.Book;
import com.codeit.springwebbasic.book.entity.BookStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class BookResponseDto {

    // 지금은 실습이라, Book과 응답 DTO의 필드가 차이가 없지만,
    // 실제로는 화면(client)단에 맞는 데이터만 정제해서 보내는 것이 일반적입니다.
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private LocalDate publishedDate;
    private BookStatus status;

    // Book 객체를 응답용 DTO로 변환해 주는 유틸 메서드
    public static BookResponseDto from(Book book) {
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publisher(book.getPublisher())
                .publishedDate(book.getPublishedDate())
                .status(book.getStatus())
                .build();
    }
}
