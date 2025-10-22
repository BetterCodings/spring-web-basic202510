package com.codeit.springwebbasic.member.controller;

import com.codeit.springwebbasic.member.dto.request.MemberCreateRequestDto;
import com.codeit.springwebbasic.member.dto.response.MemberCreateResponseDto;
import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원 가입
    // url: /api/members: POST
    // 데이터: name: string(필수), email: string(필수), phone: string(필수, 전번 형식 검사 필요)
    // 요청 DTO: MemberCreateRequestDto
    // 비즈니스로직: 이메일 중복 체크 필요, DTO를 Entity로 변환해서 멤버 저장
    // 응답: id, name, email, phone, grade, joinedAt
    // 상태 코드: 201 CREATED
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MemberCreateResponseDto> createMember(@Valid @RequestBody MemberCreateRequestDto memberCreateRequestDto) {
        Member member = memberService.memberRegister(memberCreateRequestDto);
        return new ResponseEntity<>(MemberCreateResponseDto.from(member), HttpStatus.CREATED);
    }


    // 회원 조회 (단일)
    // url: /api/members/멤버id: GET
    // 비즈니스로직: 회원 조회 후 리턴, 회원 없을 시 "회원을 찾을 수 없습니다." | 400 응답
    // 응답: 위에서 사용한 Response용 DTO로 응답 | 200 OK
    @GetMapping("/{id}")
    public ResponseEntity<MemberCreateResponseDto> getMember(@PathVariable Long id) {
        Member member = memberService.getMember(id);
        return ResponseEntity.status(HttpStatus.OK).body(MemberCreateResponseDto.from(member));
    }


    // 전체 회원 조회 & 검색
    // url: /api/members?name=xxx  | name은 전달되지 않을 수도 있습니다.
    // name이 전달되지 않으면 전체 조회, name이 전달 된다면 name이 포함된 회원을 조회.
    // 비즈니스로직: 각 상황에 맞는 Service 메서드를 호출해서 리턴.
    // 응답: 조회된 회원(Response DTO)을 리스트에 담아서 리턴 | 200 OK
    @GetMapping
    public ResponseEntity<List<MemberCreateResponseDto>> getMembers(@RequestParam(required = false) String name) {
        List<Member> member;

        if(name == null) {
            member = memberService.getAllMembers();
        } else {
            member = memberService.getMembersByName(name);
        }

        List<MemberCreateResponseDto> res = member.stream()
                .map(m -> MemberCreateResponseDto.from(m))
                .collect(Collectors.toList());

        return ResponseEntity.ok(res);
    }

}
