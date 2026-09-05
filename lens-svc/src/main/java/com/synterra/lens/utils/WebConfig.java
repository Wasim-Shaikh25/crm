package com.synterra.lens.utils;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.MultipartConfigElement;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                "/tmp", 
                50 * 1024 * 1024, 
                50 * 1024 * 1024, 
                0 
        );
        return multipartConfigElement;
    }
}

