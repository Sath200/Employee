package com.example.Employee.config

import com.google.common.base.Predicates
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = ["com.example.Employee"])
class SwaggerConfig {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            //.apis(RequestHandlerSelectors.basePackage("com.example"))
            //.paths(PathSelectors.ant("/api/*"))
            //.paths(PathSelectors.any()
            .paths(Predicates.not(PathSelectors.regex("/error.*")))
            .build()
    }
}


