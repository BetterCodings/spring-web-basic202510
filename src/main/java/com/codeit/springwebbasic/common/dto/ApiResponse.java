package com.codeit.springwebbasic.common.dto;

// 이제부터 모든 API의 응답은 이 형식을 따를 것이다.
public class ApiResponse<T> {

    // 1. 공통 비즈니스 상태코드
    // "SUCCESS", "USER_NOT_FOUND", "INVALID_INPUT" -> enum을 활용해도 괜찮다.
    private final String code;

    // 2. 비즈니스 상태 메시지 (상태 코드보다 좀 더 상세한 결과 보고)
    private final String message;

    // 3. 실제 응답하고자 하는 데이터 (제네릭 타입)
    private final T data;

    // 생성자를 private으로 막으세요. (정적 팩토리 메서드 사용 강제)
    private ApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 정적 팩토리 메서드(Static Factory Method) 추가
    // 객체를 생성할 때 생성자 호출이 아닌 static 메서드로 간접적으로 생성자를 호출.
    // 일관된 객체 생성이 가능하고, 생성자를 호출하는 것보다 메서드 이름으로 좀 더 목적을 명확하게 할 수 있습니다.

    /*
    1. 성공 응답 (데이터 포함)
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("SUCCESS", "요청에 성공했습니다.", data);
    }

    /*
    2. 성공 응답 (데이터 없음)
    null을 넣을 수도 있지만, 명시적으로 만드는 경우도 있습니다.
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>("SUCCESS", "요청에 성공했습니다.", null);
    }

    /*
    3. 실패 응답 (커스텀 코드와 메시지)
     */
    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}
