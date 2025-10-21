package com.codeit.springwebbasic.book.controller;

import com.codeit.springwebbasic.book.controller.dto.request.BookCreateRequestDto;
import com.codeit.springwebbasic.book.controller.dto.response.BookResponseDto;
import com.codeit.springwebbasic.book.entity.Book;
import com.codeit.springwebbasic.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor // final 변수를 초괴화 하는 매가값을 전달받는 생성자
public class BookController {

    private final BookService bookService;

    /*
         {
            "title": string,
            "author": string,
            "isbn": string,
            "publisher": string
            "publishedDate": date
         }
         */

    @RequestMapping(value = "/api/books", method = RequestMethod.POST)
    public BookResponseDto createBook(@Valid @RequestBody BookCreateRequestDto requestDto) {
        Book book = bookService.createBook(requestDto);
        return BookResponseDto.from(book);
    }
}
