package com.kloia.aspect;

import com.kloia.aspect.annotation.AttributeExtractor;
import com.kloia.aspect.component.RequestScopedAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RequestContextAspect {

    private final RequestScopedAttributes requestScopedAttributes;

    @Before(value = "@annotation(attributeExtractor)")
    public void process(JoinPoint joinPoint, AttributeExtractor attributeExtractor) {
        try {
            HttpServletRequest request = getRequest(joinPoint);
            requestScopedAttributes.setUserId(getHeader(request, "userId"));
            requestScopedAttributes.setStudentId((String) getArgumentMap(joinPoint).get("studentId"));
        } catch (Throwable throwable) {
            log.info("An error occurred during attribute extraction : " + attributeExtractor.toString(), throwable);
        }
    }

    private HttpServletRequest getRequest(JoinPoint joinPoint) {
        Map<String, Object> argumentMap = new HashMap<>();
        Object[] parameterValues = joinPoint.getArgs();
        String[] parameterNames = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getParameterNames();
        for (Object object : parameterValues) {
            if (object instanceof HttpServletRequest) {
                return (HttpServletRequest) object;
            }
        }
        return null;
    }

    private String getHeader(HttpServletRequest request, String key) {
        if (request != null) {
            return Optional.ofNullable(request.getHeader(key)).orElse("");
        }
        return "";
    }

    private Map<String, Object> getArgumentMap(JoinPoint joinPoint) {
        Map<String, Object> argumentMap = new HashMap<>();
        Object[] parameterValues = joinPoint.getArgs();
        String[] parameterNames = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < parameterNames.length; ++i) {
            argumentMap.put(parameterNames[i], parameterValues[i]);
        }
        return argumentMap;
    }
}

