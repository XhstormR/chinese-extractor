package com.xhstormr.web.domain.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "词典单元过滤请求")
data class LexiconLinePredicateRequest(
    @field:Schema(description = "词典单元")
    val line: String?
)
