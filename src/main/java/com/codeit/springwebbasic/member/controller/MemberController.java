package com.codeit.springwebbasic.member.controller;

import com.codeit.springwebbasic.common.dto.ApiResponse;
import com.codeit.springwebbasic.member.dto.request.MemberCreateRequestDto;
import com.codeit.springwebbasic.member.dto.response.MemberResponseDto;
import com.codeit.springwebbasic.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
//    Logger log = LoggerFactory.getLogger(MemberController.class);

    // 회원 가입
    // url: /api/members: POST
    // 데이터: name: string(필수), email: string(필수), phone: string(필수, 전번 형식 검사 필요)
    // 요청 DTO: MemberCreateRequestDto
    // 비즈니스로직: 이메일 중복 체크 필요, DTO를 Entity로 변환해서 멤버 저장
    // 응답: id, name, email, phone, grade, joinedAt
    // 상태 코드: 201 CREATED
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<MemberResponseDto>> createMember(@Valid @RequestBody MemberCreateRequestDto memberCreateRequestDto) {
        log.info("/api/members: POST, dto: {}", memberCreateRequestDto);
        MemberResponseDto member = memberService.createMember(memberCreateRequestDto);
        ApiResponse<MemberResponseDto> response = ApiResponse.success(member);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // 회원 조회 (단일)
    // url: /api/members/멤버id: GET
    // 비즈니스로직: 회원 조회 후 리턴, 회원 없을 시 "회원을 찾을 수 없습니다." | 400 응답
    // 응답: 위에서 사용한 Response용 DTO로 응답 | 200 OK
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberResponseDto>> getMember(@PathVariable Long id) {
        MemberResponseDto member = memberService.getMember(id);
        ApiResponse<MemberResponseDto> response = ApiResponse.success(member);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // 전체 회원 조회 & 검색
    // url: /api/members?name=xxx  | name은 전달되지 않을 수도 있습니다.
    // name이 전달되지 않으면 전체 조회, name이 전달 된다면 name이 포함된 회원을 조회.
    // 비즈니스로직: 각 상황에 맞는 Service 메서드를 호출해서 리턴.
    // 응답: 조회된 회원(Response DTO)을 리스트에 담아서 리턴 | 200 OK
    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberResponseDto>>> getMembers(@RequestParam(required = false) String name) {
        List<MemberResponseDto> member
                = name != null
                ? memberService.getMembersByName(name) : memberService.getAllMembers();

//        if(name == null) {
//            member = memberService.getAllMembers();
//        } else {
//            member = memberService.getMembersByName(name);
//        }

        ApiResponse<List<MemberResponseDto>> response = ApiResponse.success(member);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
