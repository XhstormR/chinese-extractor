package com.xhstormr.web.domain.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("词典更新请求")
data class LexiconUpdateRequest(
    @field:ApiModelProperty("词典类型")
    val textType: TextType,
    @field:ApiModelProperty("词典内容")
    val content: String
)
