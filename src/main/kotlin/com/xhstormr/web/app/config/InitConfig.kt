package com.xhstormr.web.app.config

import com.xhstormr.web.domain.util.getLogger
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import java.io.FileNotFoundException
import java.nio.file.Files

@Configuration
class InitConfig {

    private companion object {
        val LOGGER = getLogger()
    }

    @Bean
    fun init(
        extractorProperties: ExtractorProperties,
        environment: Environment
    ) = CommandLineRunner {
        val (textBin, diecBin) = extractorProperties
        require(Files.exists(textBin)) { FileNotFoundException(textBin.toString()) }
        require(Files.exists(diecBin)) { FileNotFoundException(diecBin.toString()) }

        LOGGER.info(
            "Listening on {}:{}",
            environment.getProperty("server.address", "0.0.0.0"),
            environment.getProperty("server.port")
        )
    }
}
