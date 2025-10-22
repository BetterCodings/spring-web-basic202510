package com.codeit.springwebbasic.member.dto.request;

import com.codeit.springwebbasic.member.entity.MemberGrade;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateRequestDto {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    // 이메일 형식인지 검사해준다. -> @가 포함되어있는가 정도, 정밀 검사를 원하면 정규표현식을 써야한다
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "000-0000-0000 형식에 맞게 전화번호를 작성해주세요.")
    private String phone;
}
