package com.xhstormr.web.domain.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "词典更新请求")
data class LexiconUpdateRequest(
    @field:Schema(description = "词典类型")
    val textType: TextType,
    @field:Schema(description = "词典内容")
    val content: String
)
