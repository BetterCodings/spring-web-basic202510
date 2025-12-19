package com.codeit.springwebbasic.member.service;

import com.codeit.springwebbasic.member.dto.request.MemberCreateRequestDto;
import com.codeit.springwebbasic.member.dto.response.MemberResponseDto;
import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.member.entity.MemberGrade;
import com.codeit.springwebbasic.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final S3FileService s3FileService;

    public MemberResponseDto createMember(MemberCreateRequestDto memberCreateRequestDto, MultipartFile file) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";

        if(!memberCreateRequestDto.email().matches(regex)) {
            throw new IllegalArgumentException("형식이 맞지 않는 이메일 입니다. 다시 입력해주세요.");
        } else if (memberRepository.existsByEmail(memberCreateRequestDto.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다. 다시 입력해주세요.");
        }

        // AWS S3와 연동해서 프로필 파일을 전송하는 로직
        try {
            // 이 url을 DB에 회원정보와 함께 저장
            // 나중에 프론트에서 회원 정보를 요청할 때 url도 같이 전달 -> 프론트에서 <img> 등으로 요청을 보낼 것
            String url = s3FileService.uploadFileToFolder(file, "users/profile");
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        Member member = Member.builder()
                .name(memberCreateRequestDto.name())
                .email(memberCreateRequestDto.email())
                .phone(memberCreateRequestDto.phone())
                .grade(MemberGrade.BRONZE)
                .joinedAt(LocalDate.now())
                .build();

        memberRepository.save(member);

        return MemberResponseDto.from(member);

    }

    public MemberResponseDto getMember(Long id){
//        if(memberRepository.findById(id).isPresent()) {
//            return memberRepository.findById(id).get();
//        } else {
//            throw new IllegalArgumentException("해당 아이디를 가진 회원이 없습니다.");
//        }

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Entity Not Found");
                    return new IllegalArgumentException("해당 아이디를 가진 회원이 없습니다.");
                });

        return MemberResponseDto.from(member);
    }

    public List<MemberResponseDto> getMembersByName(String name){
        List<Member> memberList = memberRepository.findByNameContaining(name);
        return getMemberResponseDtos(memberList);
    }

    public List<MemberResponseDto> getAllMembers(){
        return getMemberResponseDtos(memberRepository.findAll());
    }

    private List<MemberResponseDto> getMemberResponseDtos(List<Member> memberList) {
        return memberList.stream()
                .map(m -> MemberResponseDto.from(m))
                .collect(Collectors.toList());
    }
}
