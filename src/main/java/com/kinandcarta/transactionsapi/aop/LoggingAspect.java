package com.kinandcarta.transactionsapi.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    public static final String EXECUTION_REPOSITORY = "execution(* com.kinandcarta.transactionsapi.repository..*(..)))";

    @Around(EXECUTION_REPOSITORY)
    public Object logMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final StopWatch stopWatch = new StopWatch();

        Object result = callMethod(proceedingJoinPoint, stopWatch);
        logTimeTaken(proceedingJoinPoint, stopWatch);

        return result;
    }

    private Object callMethod(ProceedingJoinPoint proceedingJoinPoint, StopWatch stopWatch) throws Throwable {
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        return result;
    }

    private void logTimeTaken(ProceedingJoinPoint proceedingJoinPoint, StopWatch stopWatch) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        log.info("Execution time of "
                + stopWatch.getTotalTimeMillis()
                + " ms for "
                + methodSignature.getDeclaringType().getSimpleName()
                + "::"
                + methodSignature.getName()
        );
    }
}
