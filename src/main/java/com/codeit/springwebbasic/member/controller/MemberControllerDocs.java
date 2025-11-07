package com.codeit.springwebbasic.member.controller;

import com.codeit.springwebbasic.common.dto.ApiResponse;
import com.codeit.springwebbasic.member.dto.request.MemberCreateRequestDto;
import com.codeit.springwebbasic.member.dto.response.MemberResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// Swagger 전용 인터페이스를 하나 선언에서 비즈니스 로직과 문서화 로직 분리
// 기존 컨트롤러는 본연의 역할에 집중
@Tag(name = "사용자 관리(Member Controller)", description = "사용자 회원가입, 사용자 조회(단건, 다건) 등을 관리하는 API 입니다.")
public interface MemberControllerDocs {

    @Operation(
            summary = "회원가입",
            description = """
                    새로운 사용자를 등록합니다.
                    
                    ## 요청 데이터
                    - 이름, 이메일, 전화번호 정보가 필요합니다.
                    - 모든 정보는 필수 값입니다.
                    - 이메일은 중복될 수 없습니다.
                    
                    ## 응답 데이터
                    - 성공 시 저장된 회원 정보가 반환됩니다.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "회원 가입 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "code": "SUCCESS",
                                                "message": "요청에 성공했습니다.",
                                                "data": {
                                                    "id": 1,
                                                    "name": "김춘식",
                                                    "email": "kim1234@naver.com",
                                                    "phone": "010-2222-8888",
                                                    "grade": "BRONZE",
                                                    "joinedAt": "2025-11-07"
                                                }
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청(이메일 중복, 유효성 검사 실패 등)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "code": "ILLEGAL_ARGS",
                                                "message": "이미 존재하는 이메일입니다. 다시 입력해주세요.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse<MemberResponseDto>> createMember(
            @Valid @RequestBody MemberCreateRequestDto memberCreateRequestDto);

    ResponseEntity<ApiResponse<MemberResponseDto>> getMember(@PathVariable Long id);

    // DTO(JSON)로 전달받는 데이터는 직접 클래스에 @Schema 적용
    // 낱개로 전달되는 데이터는 @Parameter로 설명 추가 -> json을 받을 때는 적합하지 않다
    ResponseEntity<ApiResponse<List<MemberResponseDto>>> getMembers(
            @Parameter(description = "회원 조회 (이름)", required = false)
            @RequestParam(required = false) String name);

}
