package com.codeit.springwebbasic.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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
    @Around("execution(* com.codeit.springwebbasic.*.controller.*Controller.*(..))")
    public Object logControllerCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        // ProceedingJoinPoint: 이 AOP가 적용되는 지점(메서드)에 대한 정보를 담고 있는 객체

        // 3. 공통 기능 (시작)
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName(); // 메서드 이름
        Object[] args = joinPoint.getArgs(); // 메서드에 전달된 매개값들
        Signature signature = joinPoint.getSignature();
        System.out.println("signature = " + signature);

//        signature.getDeclaringType() 패키지 + 클래스 이름 (getPackageName(), getSimpleName())
//        joinPoint.getTarget(); 실제 대상 객체(Bean) 가져오기

        System.out.println("메서드 이름: " + methodName);
        System.out.println("매개값: "+ Arrays.toString(args));

        // 4. 핵심 기능 실행 (원래의 메서드 기능을 실행해라!)
        Object result = joinPoint.proceed();

        // 5. 공통 기능 (종료 및 로그)
        long endTime = System.currentTimeMillis();
        System.out.println("실행 시간: " + (endTime - start) + "ms");

        return result; // 원래 메서드가 반환하는 값을 그대로 반환
    }

    // @Before: 핵심 기능이 실행되기 직전까지만 딱 실행됨.
    // proceed()를 따로 호출하지 않습니다.

    // @AfterReturning: 메서드 정상 종료 이후 실행할 내용

    // 저 위의 두 개를 한꺼번에 아우를 수 있는 기능이 @Around

}
