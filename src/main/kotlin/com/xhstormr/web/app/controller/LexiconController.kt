package com.xhstormr.web.app.controller

import com.xhstormr.web.app.common.Const
import com.xhstormr.web.domain.model.LexiconLinePredicateRequest
import com.xhstormr.web.domain.model.LexiconLineUpdateRequest
import com.xhstormr.web.domain.model.LexiconUpdateRequest
import com.xhstormr.web.domain.model.PageRequest
import com.xhstormr.web.domain.model.RestResponse
import com.xhstormr.web.domain.model.TextType
import com.xhstormr.web.domain.service.LexiconService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * @author zhangzf
 * @create 2020/7/16 13:12
 */
@Tag(name = "词典接口")
@RestController
@RequestMapping("/lexicon", produces = [MediaType.APPLICATION_JSON_VALUE])
class LexiconController(
    private val lexiconService: LexiconService
) : BaseController() {

    @Operation(summary = "获取词典")
    @GetMapping
    fun getContent(
        @Parameter(description = "词典类型") textType: TextType
    ) =
        RestResponse.ok(lexiconService.getContent(textType))

    @Operation(summary = "保存词典")
    @PutMapping
    fun saveContent(
        @Parameter(description = "词典更新请求") @RequestBody request: LexiconUpdateRequest
    ) =
        lexiconService.saveContent(request.textType, request.content)

    @Operation(summary = "获取分页词典")
    @GetMapping("/page")
    fun getPageContent(
        @Parameter(hidden = true) assembler: PagedResourcesAssembler<String>,
        @Parameter(hidden = true) @PageableDefault(sort = [Const.DEFAULT_SORT]) pageable: Pageable,
        @Parameter(description = "词典类型") textType: TextType,
        @Parameter(description = "分页请求") @Valid pageRequest: PageRequest,
        @Parameter(description = "词典单元过滤请求") @Valid predicate: LexiconLinePredicateRequest,
    ) =
        assembler.toModel(lexiconService.getPageContent(textType, pageable, predicate))

    @Operation(summary = "添加词典单元")
    @PostMapping
    fun addLines(
        @Parameter(description = "词典单元修改请求") @RequestBody request: LexiconLineUpdateRequest
    ) =
        lexiconService.addLines(request.textType, request.lines)

    @Operation(summary = "删除词典单元")
    @DeleteMapping
    fun deleteLines(
        @Parameter(description = "词典单元修改请求") @RequestBody request: LexiconLineUpdateRequest
    ) =
        lexiconService.deleteLines(request.textType, request.lines)
}
