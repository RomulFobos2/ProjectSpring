package com.company.ProjectSpring.securityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Класс не используется вроде как)
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/login").setViewName("login");
        //registry.addViewController("/news").setViewName("news");
    }
}