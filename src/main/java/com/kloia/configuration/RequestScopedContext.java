package com.kloia.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class RequestScopedContext {

    public static final String REQUEST_SCOPED_ATTRIBUTES = "requestScopedAttributes";

    public static RequestScopedAttributes get() {
        try {
            HttpServletRequest httpServletRequest = getCurrentHttpRequest();
            return (RequestScopedAttributes) httpServletRequest.getAttribute(REQUEST_SCOPED_ATTRIBUTES);
        } catch (Exception e) {
            return null;
        }
    }

    public static void set(HttpServletRequest httpServletRequest, RequestScopedAttributes requestScopedAttributes) {
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
