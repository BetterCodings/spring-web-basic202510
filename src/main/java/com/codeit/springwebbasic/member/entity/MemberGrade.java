package com.codeit.springwebbasic.member.entity;

public enum MemberGrade {
    BRONZE(0),
    SILVER(5),
    GOLD(15),
    PLATINUM(30);

    private final int requiredRentals;

    MemberGrade(int requiredRentals) {
        this.requiredRentals = requiredRentals;
    }

    public MemberGrade upgrade() {
        // 새로운 switch 표현식
        return switch (this){
            case BRONZE -> SILVER;
            case SILVER -> GOLD;
            case GOLD, PLATINUM -> PLATINUM;
        };
    }
}
