package com.infobip.pmf.course.mavencentral.config;

import com.infobip.pmf.course.mavencentral.api.ApiKeyAuth;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Bean
    public FilterRegistrationBean<ApiKeyAuth> apiKeyAuthFilterRegistrationBean(ApiKeyAuth apiKeyAuth) {
        FilterRegistrationBean<ApiKeyAuth> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(apiKeyAuth);
        registrationBean.addUrlPatterns("/libraries/*", "/libraries/*/versions/*");
        return registrationBean;
    }
}
