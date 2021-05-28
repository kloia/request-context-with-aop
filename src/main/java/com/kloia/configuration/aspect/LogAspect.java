package com.kloia.configuration.aspect;

import com.kloia.configuration.CustomContext;
import com.kloia.configuration.RequestScopedAttributes;
import com.kloia.configuration.RequestScopedContext;
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
        prepareLogFromCustomContext(proceedingJoinPoint);
        return proceedingJoinPoint.proceed();
    }

    @Around(value = "servicePackagePointcut()")
    public Object serviceAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        prepareLogFromCustomContext(proceedingJoinPoint);
        return proceedingJoinPoint.proceed();
    }

    public void prepareLogFromRequestScopedContext(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String name = signature.getMethod().getDeclaringClass().getSimpleName() + ":" + signature.getMethod().getName();
        RequestScopedAttributes requestScopedAttributes = RequestScopedContext.get();
        //System.out.println("On Method [" + name + "] - User ID is " + requestScopedAttributes.getUserId());
    }

    public void prepareLogFromCustomContext(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String name = signature.getMethod().getDeclaringClass().getSimpleName() + ":" + signature.getMethod().getName();
        RequestScopedAttributes requestScopedAttributes = CustomContext.get();
        //System.out.println("On Method [" + name + "] - User ID is " + requestScopedAttributes.getUserId());
    }

}
