package com.codeit.springwebbasic.book.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


// 어노테이션 사용 시 이 클래스에서 검증 로직 실행.
// ConstraintValidator<어노테이션, 검사하고자 하는 값의 타입>
public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private ValidEnum annotation; // 어노테이션에 설정된 값을 저장할 변수

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        // DTO에 붙여놓은 어노테이션 정보를 가져와서 멤버변수에 저장하는 역할
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 실전 검사 (핵심 로직)
        if (value == null) {
            return true; // null은 @NotNull이나 @NotBlank가 처리하도록 통과
        }

        // 사용자가 지정한 Enum 클래스를 annotation 변수에서 꺼내기.
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues != null) {
            for (Object enumValue : enumValues) {

                // 사용자가 요청과 함께 전달한 값(value) vs Enum 상수 이름 (enumValue.toString)
                // 만약 어노테이션에 ignoreCase 를 true로 주었다면 equalsIgnoreCase로 대소문자 구분 없이 비교하겠다.
                if (value.equals(enumValue.toString())
                        || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
                    return true;
                }
            }
        }

        return false;
    }
}
