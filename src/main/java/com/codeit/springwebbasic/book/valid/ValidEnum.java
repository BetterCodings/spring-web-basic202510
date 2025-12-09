package com.codeit.springwebbasic.book.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER}) // 어노테이션의 적용 타겟(필드와 파라미터에 사용 가능)
@Retention(RetentionPolicy.RUNTIME) // 프로그램 실행 중에 항상 적용되어야 한다.(수명) (거의 필수 값이라고 생각)
@Constraint(validatedBy = EnumValidator.class) // 이 어노테이션이 붙은 값의 검사는 누가 담당하는가?
public @interface ValidEnum {

    Class<? extends Enum<?>> enumClass();

    // 대소문자 구분 여부
    // 사용할 때 : @ValidEnum(ignoreCase = true)
    // 안 적으면 기본 값 false
    boolean ignoreCase() default false;

    // --- 필수 3요소 (규칙처럼 사용) ---
    String message() default "유효하지 않은 값 입니다"; // 검증 실패 시 보여줄 기본 메시지
    Class<?>[] groups() default {}; // 상황별 검증 그룹 (거의 초기에는 {} 빈 값으로 둠)
    Class<? extends Payload>[] payload() default {}; // 메타 데이터 (거의 {} 빈 값으로 둠)
}
