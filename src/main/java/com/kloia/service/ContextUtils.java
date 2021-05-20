package com.kloia.service;

import com.kloia.configuration.RequestScopedAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class ContextUtils {

    public static final String REQUEST_SCOPED_ATTRIBUTES = "requestScopedAttributes";

    public static RequestScopedAttributes getRequestContext() {
        try {
            HttpServletRequest httpServletRequest = getCurrentHttpRequest();
            return (RequestScopedAttributes) httpServletRequest.getAttribute(REQUEST_SCOPED_ATTRIBUTES);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setRequestContext(HttpServletRequest httpServletRequest, RequestScopedAttributes requestScopedAttributes) {
        httpServletRequest.setAttribute(REQUEST_SCOPED_ATTRIBUTES, requestScopedAttributes);
    }

    public static HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

}
