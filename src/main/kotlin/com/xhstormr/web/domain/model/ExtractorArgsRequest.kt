package com.xhstormr.web.domain.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "提取参数请求")
data class ExtractorArgsRequest(
    @field:Schema(description = "整词匹配")
    val boundary: Boolean,
    @field:Schema(description = "忽略大小写")
    val ignoreCase: Boolean,
)
