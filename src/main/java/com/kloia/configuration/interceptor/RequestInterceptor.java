package com.kloia.configuration.interceptor;

import com.kloia.configuration.RequestScopedAttributes;
import com.kloia.service.ContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Resource(name = "requestScopedAttributes")
    private RequestScopedAttributes requestScopedAttributes;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        requestScopedAttributes.setUserId(getHeader(request, "userId"));
        requestScopedAttributes.setStudentId(getFirstParameter(request, "studentId"));

        ContextUtils.setRequestContext(requestScopedAttributes);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        RequestContextHolder.resetRequestAttributes();
    }


    private String getHeader(HttpServletRequest request, String key) {
        if (request != null) {
            return Optional.ofNullable(request.getHeader(key)).orElse("");
        }
        return "";
    }

    private String getFirstParameter(HttpServletRequest request, String key) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (ArrayUtils.isEmpty(parameterMap.get(key))) {
            return "";
        }
        return parameterMap.get(key)[0];
    }

}
