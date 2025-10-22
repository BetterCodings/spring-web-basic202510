package com.codeit.springwebbasic.member.service;

import com.codeit.springwebbasic.member.dto.request.MemberCreateRequestDto;
import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.member.entity.MemberGrade;
import com.codeit.springwebbasic.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member memberRegister(MemberCreateRequestDto memberCreateRequestDto) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";

        if(!memberCreateRequestDto.getEmail().matches(regex)) {
            throw new IllegalArgumentException("형식이 맞지 않는 이메일 입니다. 다시 입력해주세요.");
        } else if (memberRepository.existsByEmail(memberCreateRequestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다. 다시 입력해주세요.");
        } else {
            Member member = Member.builder()
                    .name(memberCreateRequestDto.getName())
                    .email(memberCreateRequestDto.getEmail())
                    .phone(memberCreateRequestDto.getPhone())
                    .grade(MemberGrade.BRONZE)
                    .joinedAt(LocalDate.now())
                    .build();

            return memberRepository.save(member);
        }
    }

    public Member getMember(Long id){
//        if(memberRepository.findById(id).isPresent()) {
//            return memberRepository.findById(id).get();
//        } else {
//            throw new IllegalArgumentException("해당 아이디를 가진 회원이 없습니다.");
//        }

        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 가진 회원이 없습니다."));
    }

    public List<Member> getMembersByName(String name){
        return memberRepository.findByNameContaining(name);
    }

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }



}
