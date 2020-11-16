package com.xhstormr.web.app.config

import com.xhstormr.web.app.controller.HomeController
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springdoc.core.SpringDocUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author zhangzf
 * @create 2018/6/19 16:43
 */
@Configuration
class SwaggerConfig {

    init {
        SpringDocUtils.getConfig().addRestControllers(HomeController::class.java)
    }

    @Bean
    fun api() = OpenAPI()
        .info(apiInfo())
        .externalDocs(docs())

    private fun apiInfo() = Info()
        .title("API 文档")
        .description("API 文档")
        .version("1.0")
        .license(License().name("Apache License 2.0"))

    private fun docs() = ExternalDocumentation()
        .url("https://github.com/XhstormR")
        .description("XhstormR")
}
