package com.codeit.springwebbasic.rental.controller;

import com.codeit.springwebbasic.rental.dto.request.RentalRequestDto;
import com.codeit.springwebbasic.rental.dto.response.RentalResponseDto;
import com.codeit.springwebbasic.rental.entity.Rental;
import com.codeit.springwebbasic.rental.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    // 도서 대여
    @PostMapping
    public ResponseEntity<RentalResponseDto> rentBook(@Valid @RequestBody RentalRequestDto requestDto) {
        Rental rental = rentalService.rentBook(requestDto.getMemberId(), requestDto.getBookId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RentalResponseDto.from(rental));
    }
}
