package com.xhstormr.web.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

/**
 * @author zhangzf
 * @create 2018/6/19 16:43
 */
@Import(BeanValidatorPluginsConfiguration::class)
@Configuration
class SwaggerConfig {

    @Bean
    fun api(): Docket = Docket(DocumentationType.OAS_30)
        .groupName("business-api")
        .pathMapping("../")
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("de.codecentric.boot").negate())
        .apis(RequestHandlerSelectors.basePackage("org.springframework.boot").negate())
        .build()

    private fun apiInfo() = ApiInfoBuilder()
        .title("API 文档")
        .description("API 文档")
        .license("Apache License 2.0")
        .licenseUrl("")
        .termsOfServiceUrl("")
        .version("1.0")
        .contact(Contact("XhstormR", "https://github.com/XhstormR", ""))
        .build()
}
