package com.xhstormr.web.app.controller

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.xhstormr.web.domain.model.ExtractorArgs
import com.xhstormr.web.domain.model.ExtractorArgsRequest
import com.xhstormr.web.domain.model.ExtractorType
import com.xhstormr.web.domain.service.ExtractorService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * @author zhangzf
 * @create 2020/7/16 13:12
 */
@Tag(name = "提取接口")
@RestController
@RequestMapping("/extractor", produces = [MediaType.APPLICATION_JSON_VALUE])
class ExtractorController(
    private val extractorService: ExtractorService,
    private val objectMapper: ObjectMapper
) : BaseController() {

    @Operation(summary = "提取所有")
    @PostMapping("/all", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun extractAll(
        @Parameter(description = "文件") @RequestPart file: MultipartFile,
        // TODO: workaround, see https://github.com/springdoc/springdoc-openapi/issues/820
        @Parameter(description = "提取参数请求", schema = Schema(type = "string", format = "binary"))
        @RequestPart request: ExtractorArgsRequest,
    ) =
        extractText(file, ExtractorType.All, request)

    @Operation(summary = "提取中文")
    @PostMapping("/zh", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun extractChinese(
        @Parameter(description = "文件") @RequestPart file: MultipartFile,
        @Parameter(description = "提取参数请求", schema = Schema(type = "string", format = "binary"))
        @RequestPart request: ExtractorArgsRequest,
    ) =
        extractText(file, ExtractorType.ZH, request)

    @Operation(summary = "提取英文")
    @PostMapping("/en", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun extractEnglish(
        @Parameter(description = "文件") @RequestPart file: MultipartFile,
        @Parameter(description = "提取参数请求", schema = Schema(type = "string", format = "binary"))
        @RequestPart request: ExtractorArgsRequest,
    ) =
        extractText(file, ExtractorType.EN, request)

    @Operation(summary = "提取漏洞编号")
    @PostMapping("/vulId", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun extractVulId(
        @Parameter(description = "文件") @RequestPart file: MultipartFile,
        @Parameter(description = "提取参数请求", schema = Schema(type = "string", format = "binary"))
        @RequestPart request: ExtractorArgsRequest,
    ) =
        extractText(file, ExtractorType.VulId, request)

    @Operation(summary = "提取日期")
    @PostMapping("/date", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun extractDate(
        @Parameter(description = "文件") @RequestPart file: MultipartFile,
        @Parameter(description = "提取参数请求", schema = Schema(type = "string", format = "binary"))
        @RequestPart request: ExtractorArgsRequest,
    ) =
        extractText(file, ExtractorType.Date, request)

    @Operation(summary = "提取域名")
    @PostMapping("/domain", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun extractDomain(
        @Parameter(description = "文件") @RequestPart file: MultipartFile,
        @Parameter(description = "提取参数请求") @RequestPart request: ExtractorArgsRequest,
    ) =
        extractText(file, ExtractorType.Domain, request)

    @Operation(summary = "提取类型")
    @PostMapping("/type")
    fun extractType(
        @Parameter(description = "文件") @RequestPart file: MultipartFile,
    ): JsonNode {
        val tempFile = createTempFile().apply { file.transferTo(this) }
        try {
            return objectMapper.readTree(extractorService.extractType(tempFile.path))
        } finally {
            tempFile.delete()
        }
    }

    private fun extractText(
        file: MultipartFile,
        type: ExtractorType,
        request: ExtractorArgsRequest
    ): JsonNode {
        val tempFile = createTempFile().apply { file.transferTo(this) }
        try {
            val args = ExtractorArgs(tempFile.path, type, request.boundary, request.ignoreCase)
            val json = extractorService.extractText(args)
            return objectMapper.readTree(json)
        } finally {
            tempFile.delete()
        }
    }
}
