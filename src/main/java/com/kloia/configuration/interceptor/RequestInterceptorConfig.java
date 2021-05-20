package com.kloia.configuration.interceptor;

import com.kloia.configuration.RequestScopedAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class RequestInterceptorConfig implements WebMvcConfigurer {

    @Bean
    public RequestInterceptor getRequestInterceptor() {
        return new RequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getRequestInterceptor()).addPathPatterns("/**");
    }

    @Bean
    @RequestScope
    public RequestScopedAttributes requestScopedAttributes() {
        return new RequestScopedAttributes();
    }

}