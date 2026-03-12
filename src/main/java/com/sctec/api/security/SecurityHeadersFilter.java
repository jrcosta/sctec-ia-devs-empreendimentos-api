package com.sctec.api.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityHeadersFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // Add security headers
        httpServletResponse.setHeader("X-Content-Type-Options", "nosniff");
        httpServletResponse.setHeader("X-Frame-Options", "DENY");
        httpServletResponse.setHeader("X-XSS-Protection", "1; mode=block");
        httpServletResponse.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        httpServletResponse.setHeader("Content-Security-Policy", "default-src 'self'");

        chain.doFilter(request, response);
    }
}
