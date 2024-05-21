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

    public ApiKeyAuth(UserService userService) {
        this.userService = userService;
    }
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String apiKey = request.getHeader("Authorization");
        if (apiKey == null || !apiKey.startsWith("App ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid API key.");
            return;
        }

        //String key = apiKey.substring(4);
        if (userService.findByApiKey(apiKey).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid API key.");
            return;
        }
        chain.doFilter(request, response);
    }
}
