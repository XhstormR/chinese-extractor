package com.xhstormr.web.app.config

import com.xhstormr.web.app.config.handler.RequestLogger
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@ConfigurationPropertiesScan
@Configuration
class WebConfig : WebMvcConfigurer {

    @Bean
    fun requestLogger() = RequestLogger()
}
