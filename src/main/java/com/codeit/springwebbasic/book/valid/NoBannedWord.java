package com.codeit.springwebbasic.book.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BannedWordValidator.class)
public @interface NoBannedWord {

   String message() default "유효하지 않은 값 입니다.";
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};
}
