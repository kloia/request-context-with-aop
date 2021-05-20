package com.kloia.configuration.aspect;

import com.kloia.configuration.RequestScopedAttributes;
import com.kloia.service.ContextUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

@Aspect
@Service
@RequiredArgsConstructor
public class LogAspect {

    /**
     * Pointcut that matches all Spring beans in the controller packages.
     */
    @Pointcut("within(com.kloia.controller..*)")
    public void controllerPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the
        // advices.
    }

    /**
     * Pointcut that matches all Spring beans in the service packages.
     */
    @Pointcut("within(com.kloia.service..*)")
    public void servicePackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the
        // advices.
    }

    @Around("controllerPackagePointcut()")
    public Object controllerAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        prepareLogFromStaticMethod(proceedingJoinPoint);
        return proceedingJoinPoint.proceed();
    }

    @Around(value = "servicePackagePointcut()")
    public Object serviceAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        prepareLogFromStaticMethod(proceedingJoinPoint);
        return proceedingJoinPoint.proceed();
    }

    public void prepareLogFromStaticMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String name = signature.getClass().getSimpleName() + ":" + signature.getMethod().getName();
        RequestScopedAttributes requestScopedAttributes = ContextUtils.getRequestContext();
        System.out.println("On Method [" + name + "] - User ID is " + requestScopedAttributes.getUserId());
    }

    public void prepareLogFromBean(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String methodName = signature.getMethod().getName();
//        System.out.println("On Service [" + methodName + "] - User ID is " + this.requestScopedAttributes.getUserId());
    }
}
