package com.xhstormr.web.domain.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "词典单元修改请求")
data class LexiconLineUpdateRequest(
    @field:Schema(description = "词典类型")
    val textType: TextType,
    @field:Schema(description = "词典单元")
    val lines: List<String>
)
