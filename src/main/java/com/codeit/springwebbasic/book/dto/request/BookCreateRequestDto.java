package com.codeit.springwebbasic.book.dto.request;

import com.codeit.springwebbasic.book.valid.NoBannedWord;
import com.codeit.springwebbasic.book.valid.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// DTO (DaTa Transfer Object)
// 비즈니스 로직 없이 순수하게 전달하고 싶은 데이터를 담는 용도로 사용하는 객체
// 요청 DTO, 응답 DTO로 나누어 사용. 꼭 필요한 데이터만 정제해서 전달하는 용도로 사용합니다.
// Entity를 직접 사용하지 않는 이유는
// 1. 필요없는 정보까지 노출될 위험 2. 도메인이 바뀌면 다른 스펙도 전부 변경 3. 검증 로직 배치의 애매함.
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequestDto {

    /*
     @NotNull: Null만 허용하지 않습니다. ""(빈 문자열), " "(공백)은 허용
     @NotEmpty: Null과 ""(빈 문자열) 둘 다 허용하지 않습니다. " "(공백)은 허용
     @NotBlank: Null, "", " " 전부 허용하지 않습니다.
     */

    @NotBlank(message = "제목은 필수입니다.")
//    @Size(min = 2, max = 10, message = "title은 2글자 이상 10글자 이하여야 합니다.")
    @NoBannedWord(message = "제목에 비속어를 포함할 수 없습니다.")
    private String title;

    @NotBlank(message = "저자는 필수입니다.")
    private String author;

    @NotBlank(message = "ISBN은 필수입니다.")
    @Pattern(regexp = "\\d{13}", message = "ISBN은 13자리 숫자여야 합니다.")
    private String isbn;

    @NotBlank(message = "출판사는 필수입니다.")
    private String publisher;

    @PastOrPresent(message = "출판일은 과거 또는 현재여야 합니다.")
    private LocalDate publishedDate;

    // 검증 값은 Enum이지만, 필드 타입은 String으로 선언.
    // 타입은 Validator나 서비스에서 변환 시켜주면 된다.
    @ValidEnum(enumClass = BookType.class,
            message = "유효하지 않은 책 종류 입니다. (IT, SCIENCE, LITERATURE, NOVEL 중 하나여야 합니다.)",
            ignoreCase = true)
    private String type;
}
