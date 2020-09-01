package com.xhstormr.web.app.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.nio.file.Path

/**
 * @author zhangzf
 * @create 2018/11/5 21:18
 */
@ConstructorBinding
@ConfigurationProperties("extractor")
data class ExtractorProperties(
    val textBin: Path,
    val diecBin: Path
)
