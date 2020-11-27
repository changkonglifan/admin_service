package com.xuyang.admin;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MyConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {


/*
        registry.addInterceptor(new SessionInterceptor())
                .addPathPatterns("/bill/**")
                .addPathPatterns("/device/**")
                .addPathPatterns("/departmant/**")
                .addPathPatterns("/log/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/bill/report/test")
                .excludePathPatterns("/user/logout");

*/

    }
}
