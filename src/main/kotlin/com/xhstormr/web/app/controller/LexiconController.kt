package com.xhstormr.web.app.controller

import com.xhstormr.web.domain.model.LexiconUpdateRequest
import com.xhstormr.web.domain.model.RestResponse
import com.xhstormr.web.domain.model.TextType
import com.xhstormr.web.domain.service.LexiconService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author zhangzf
 * @create 2020/7/16 13:12
 */
@Api(tags = ["词典接口"])
@RestController
@RequestMapping("/lexicon", produces = [MediaType.APPLICATION_JSON_VALUE])
class LexiconController(
    private val lexiconService: LexiconService
) : BaseController() {

    @ApiOperation("获取词典")
    @GetMapping
    fun getContent(@ApiParam("词典类型") textType: TextType) =
        RestResponse.ok(lexiconService.getContent(textType))

    @ApiOperation("保存词典")
    @PostMapping
    fun saveContent(@ApiParam("词典更新请求") @RequestBody request: LexiconUpdateRequest) =
        lexiconService.saveContent(request.textType, request.content)
}
