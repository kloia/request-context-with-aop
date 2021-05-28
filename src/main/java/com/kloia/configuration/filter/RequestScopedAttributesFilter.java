package com.kloia.configuration.filter;

import com.kloia.configuration.CustomContext;
import com.kloia.configuration.RequestScopedAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.RequestContextFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestScopedAttributesFilter extends RequestContextFilter {

    @Override
    protected void initFilterBean() throws ServletException {
        super.setThreadContextInheritable(true);
        super.initFilterBean();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            RequestScopedAttributes requestScopedAttributes = new RequestScopedAttributes();
            requestScopedAttributes.setUserId(getHeader(request, "userId"));
            requestScopedAttributes.setStudentId(getFirstParameter(request, "studentId"));

            CustomContext.set(requestScopedAttributes);
            //RequestScopedContext.set(request, requestScopedAttributes);
        } catch (Throwable throwable) {
            log.info("An error occurred during attribute extraction : ", throwable);
        }
        chain.doFilter(request, response);

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
