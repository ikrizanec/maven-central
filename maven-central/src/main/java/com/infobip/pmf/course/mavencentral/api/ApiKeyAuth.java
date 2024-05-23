package com.infobip.pmf.course.mavencentral.api;

import com.infobip.pmf.course.mavencentral.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class ApiKeyAuth extends HttpFilter {

    private UserService userService;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String API_KEY_PREFIX = "App ";

    public ApiKeyAuth(UserService userService) {
        this.userService = userService;
    }
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String apiKey = request.getHeader(AUTHORIZATION_HEADER);

        if (apiKey == null || !apiKey.startsWith(API_KEY_PREFIX)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid API key.");
            return;
        }

        String key = apiKey.substring(API_KEY_PREFIX.length());
        if (userService.findByApiKey(key).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid API key.");
            return;
        }

        chain.doFilter(request, response);
    }
}
