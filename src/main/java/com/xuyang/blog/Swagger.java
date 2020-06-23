
/**
 * @program:
 * @description: Swagger
 * @author: xuyang
 * @create
 */

package com.xuyang.blog;


import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
public class Swagger {
    @Bean
    public Docket docket(){
        List<Parameter> pars = new ArrayList<Parameter>();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .apis(RequestHandlerSelectors.basePackage("com.xuyang.blog.controller"))
                .paths(PathSelectors.any())
                .paths(PathSelectors.regex("/.*"))// 对根下所有路径进行监控
                .paths(Predicates.not(PathSelectors.regex("/error*")))// 错误路径不监控
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("博客文档")
                .description("博客swagger")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}