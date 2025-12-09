package com.codeit.springwebbasic.book.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class BannedWordValidator implements ConstraintValidator<NoBannedWord, String> {

    // 나중에는 금지 단어들을 DB나 프로퍼티에서 가져올 수 있겠죠?
    private final List<String> BANNED_WORDS = List.of("바보", "멍청이", "메롱");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) return true;

        for (String bannedWord : BANNED_WORDS) {
            if (value.contains(bannedWord)) {
                return false;
            }
        }

        return true;
    }
}
