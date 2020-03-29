package com.zjz.graduationdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    @Bean
    public Docket accessHello() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zjz.graduationdemo.controller"))
                .paths(PathSelectors.any())
                .build();

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Base API")
                .description("APIs")
                .version("v1.0")
                .build();

        docket.apiInfo(apiInfo);
        return docket;
    }
}
