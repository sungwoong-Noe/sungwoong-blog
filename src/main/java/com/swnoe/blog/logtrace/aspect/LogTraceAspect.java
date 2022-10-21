package com.swnoe.blog.logtrace.aspect;

import com.swnoe.blog.logtrace.TraceStatus;
import com.swnoe.blog.logtrace.trace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LogTraceAspect {

    private final LogTrace logTrace;

    @Around("execution(* com.swnoe.blog.app..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        //advice 부분이 된다.
        TraceStatus status = null;

        try{

            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            //target 호출
            Object result = joinPoint.proceed();

            logTrace.end(status);

            return result;
        }catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}
