package com.codeit.springwebbasic.member.dto.response;

import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.member.entity.MemberGrade;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MemberCreateResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private MemberGrade grade;
    private LocalDate joinedAt;

    public static MemberCreateResponseDto from(Member member) {
        return MemberCreateResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .grade(member.getGrade())
                .joinedAt(member.getJoinedAt())
                .build();
    }
}
