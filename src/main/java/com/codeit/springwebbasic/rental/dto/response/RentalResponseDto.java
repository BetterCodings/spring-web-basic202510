package com.codeit.springwebbasic.rental.dto.response;

import com.codeit.springwebbasic.rental.entity.Rental;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RentalResponseDto {
    private Long id;
    private Long memberId;
    private String memberName;
    private Long bookId;
    private String bookTitle;
    private LocalDateTime rentedAt;
    private LocalDateTime dueDate;
    private LocalDateTime returnedAt;
    private boolean overdue;

    public static RentalResponseDto from(Rental rental) {
        return RentalResponseDto.builder()
                .id(rental.getId())
                .memberId(rental.getMember().getId())
                .memberName(rental.getMember().getName())
                .bookId(rental.getBook().getId())
                .bookTitle(rental.getBook().getTitle())
                .rentedAt(rental.getRentedAt())
                .dueDate(rental.getDueDate())
                .returnedAt(rental.getReturnedAt())
                .overdue(rental.isOverdue())
                .build();
    }
}
