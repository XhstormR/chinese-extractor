package com.xhstormr.web.app.controller

import com.xhstormr.web.domain.model.TextType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author zhangzf
 */
@Tag(name = "字典接口")
@RestController
@RequestMapping("/dict", produces = [MediaType.APPLICATION_JSON_VALUE])
class DictController : BaseController() {

    @Operation(summary = "词典类型")
    @GetMapping("/textType")
    fun getTextType() = TextType.values()
}
