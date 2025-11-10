package com.codeit.springwebbasic.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect // 이 클래스는 AOP 담당자(Aspect)입니다.
@Component // 빈 등록
public class LoggingAspect {

    // 1. Pointcut (어디서?)
    // excution([수식어] 리턴타입 [클래스경로.]메서드이름(파라미터) [예외]) - []는 생략이 가능한 문법
    // @Pointcut("execution(* com.codeit.springwebbasic.member.controller.MemberController.*(..))")
    // 모든 접근 제한자 허용, 모든 리턴타입 허용, member 컨트롤러 안에 있는 모든 메서드를 대상(매개값은 모든 파라미터)
    // @Pointcut("execution(* com.codeit.springwebbasic..*.*(..))")
    // ..: 0개 이상의 하위 패키지를 의미 -> 모든 하위 패키지를 전부 지목하고 싶을 때


    @Pointcut("execution(* com.codeit.springwebbasic.member.controller.MemberController.*(..))")
    private void allControllerMethods() {
        // 위에서 지정한 (어디에?) 라는 메서드 위치에 사전에 지정해야 할 여러 설정, 사전 작업 등을 명시합니다.
        // @Pointcut을 생략하고, @Around에 바로 execution을 작성해도 됩니다. -> 실제로도 많이 쓴다.
        // ex) @Around("execution(* com.codeit.springwebbasic.member.*(..))")
        System.out.println("allContollerMethods 호출!");
    }

    // 2. Advice (무엇을?): Pointcut에 지정된 곳 주변(Around)에서 이 코드를 실행.
//    @Around("execution(* com.codeit.springwebbasic.*.controller.*Controller.*(..))")
//    public Object logControllerCheck(ProceedingJoinPoint joinPoint) throws Throwable {
//        // ProceedingJoinPoint: 이 AOP가 적용되는 지점(메서드)에 대한 정보를 담고 있는 객체
//
//        // 3. 공통 기능 (시작)
//        long start = System.currentTimeMillis();
//        String methodName = joinPoint.getSignature().getName(); // 메서드 이름
//        Object[] args = joinPoint.getArgs(); // 메서드에 전달된 매개값들
//        Signature signature = joinPoint.getSignature();
//        System.out.println("signature = " + signature);
//
////        signature.getDeclaringType() 패키지 + 클래스 이름 (getPackageName(), getSimpleName())
////        joinPoint.getTarget(); 실제 대상 객체(Bean) 가져오기
//
//        System.out.println("메서드 이름: " + methodName);
//        System.out.println("매개값: "+ Arrays.toString(args));
//
//        // 4. 핵심 기능 실행 (원래의 메서드 기능을 실행해라!)
//        Object result = joinPoint.proceed();
//
//        // 5. 공통 기능 (종료 및 로그)
//        long endTime = System.currentTimeMillis();
//        System.out.println("실행 시간: " + (endTime - start) + "ms");
//
//        return result; // 원래 메서드가 반환하는 값을 그대로 반환
//    }

    // @Before: 핵심 기능이 실행되기 직전까지만 딱 실행됨.
    // proceed()를 따로 호출하지 않습니다.
    @Before("execution(* com.codeit.springwebbasic.*.controller.*Controller.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        // 3. 공통 기능 (시작)
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName(); // 메서드 이름
        Object[] args = joinPoint.getArgs(); // 메서드에 전달된 매개값들
        Signature signature = joinPoint.getSignature();
        System.out.println("signature = " + signature);
        System.out.println("메서드 이름: " + methodName);
        System.out.println("매개값: "+ Arrays.toString(args));

        HttpServletRequest request = getCurrentRequests();
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        System.out.println("requestURI = " + requestURI);
        System.out.println("method = " + method);

        // 이 메서드가 종료되면 알아서 핵심 로직이 수행됩니다.
    }

    // @AfterReturning: 메서드 정상 종료 이후 실행할 내용
    @AfterReturning(pointcut = "execution(* com.codeit.springwebbasic.*.controller.*Controller.*(..))",
                    returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("성공 메서드 이름: " + methodName);
        System.out.println("반환 값: " + result);
    }

    // 저 위의 두 개를 한꺼번에 아우를 수 있는 기능이 @Around
    // 메서드 수행 시간을 확인하기 위해서는 Around를 사용하는게 맞다 -> 분리되면 체크가 힘드니까

    // @AfterThrowing: 핵심 기능 실행 중 예외가 발생했을 때 실행됩니다.
    // 평소에 사용하는 ControllerAdvice도 AOP의 개념고 철학을 적용한 기술
    @AfterThrowing(pointcut = "execution(* com.codeit.springwebbasic.*.controller.*Controller.*(..))",
                   throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        System.out.println("에러 발생된 메서드: " + joinPoint.getSignature().getName());
        System.out.println("에러 메시지: " + ex.getMessage());

        // 나중에는 메신저(slack, discord) 등의 알림 전송 로직이 들어갈 수도 있겠죠?
    }

    // 현재 HTTP 요청 가져오기
    private HttpServletRequest getCurrentRequests() {
        ServletRequestAttributes attributes
                = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // 스프링에서 제공하는 요청 관련 정보를 담아놓은 RequestContextHolder에게
        // 요청 객체를 얻어내서 리턴
        return attributes.getRequest();
    }
}
