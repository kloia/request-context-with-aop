package com.kloia.service;

import com.kloia.configuration.RequestScopedAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class ContextUtils {

    public static final String REQUEST_SCOPED_ATTRIBUTES = "requestScopedAttributes";

    public static RequestScopedAttributes getRequestContext() {
        try {
            return (RequestScopedAttributes) RequestContextHolder.getRequestAttributes().getAttribute(REQUEST_SCOPED_ATTRIBUTES, RequestAttributes.SCOPE_REQUEST);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setRequestContext(RequestScopedAttributes requestScopedAttributes) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        requestAttributes.setAttribute(REQUEST_SCOPED_ATTRIBUTES, requestScopedAttributes, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.setRequestAttributes(requestAttributes, true);
    }

}
