package com.codeit.springwebbasic.book.service;

import com.codeit.springwebbasic.book.controller.dto.request.BookCreateRequestDto;
import com.codeit.springwebbasic.book.entity.Book;
import com.codeit.springwebbasic.book.entity.BookStatus;
import com.codeit.springwebbasic.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book createBook(BookCreateRequestDto requestDto) {
        // ISBN 중복 체크
        Optional<Book> byIsbn = bookRepository.findByIsbn(requestDto.getIsbn());
        if (byIsbn.isPresent()) {
            throw new IllegalArgumentException("이미 등록된 ISBN입니다.: " + requestDto.getIsbn());
        }

//        Book book = new Book();
//        book.setTitle(requestDto.getTitle());
//        book.setAuthor(requestDto.getAuthor());
//        book.setIsbn(requestDto.getIsbn());
//        book.setPublisher(requestDto.getPublisher());
//        book.setPublishedDate(requestDto.getPublishedDate());
//        book.setStatus(BookStatus.AVAILABLE);

        // Builder 패턴을 활용하면 초기화 순서를 내 맘대로 지정해도 상관 없고,
        // 원하는 필드만 골라서 초기화하는 것이 가능.
        Book book = Book.builder()
                .title(requestDto.getTitle())
                .author(requestDto.getAuthor())
                .isbn(requestDto.getIsbn())
                .publisher(requestDto.getPublisher())
                .publishedDate(requestDto.getPublishedDate())
                .status(BookStatus.AVAILABLE)
                .build();

        return bookRepository.save(book);
    }

    public Book getBook(Long id) {
//        if(bookRepository.findById(id).isPresent()){
//            return bookRepository.findById(id).get();
//        } else {
//            throw new IllegalArgumentException("해당 id를 가진 책이 존재하지 않습니다.");
//        }

        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("도서를 찾을 수가 없습니다."));
    }
}
