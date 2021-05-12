package com.kloia.aspect.component;

import com.kloia.aspect.annotation.AttributeExtractor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("requestContextFilter")
public class RequestContextFilter extends HttpFilter {

    @Override
    @AttributeExtractor
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
    }
}
