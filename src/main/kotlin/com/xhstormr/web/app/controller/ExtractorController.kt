package com.xhstormr.web.app.controller

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.xhstormr.web.domain.model.ExtractorArgs
import com.xhstormr.web.domain.model.ExtractorType
import com.xhstormr.web.domain.service.ExtractorService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
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
@Api(tags = ["提取接口"])
@RestController
@RequestMapping("/extractor", produces = [MediaType.APPLICATION_JSON_VALUE])
class ExtractorController(
    private val extractorService: ExtractorService,
    private val objectMapper: ObjectMapper
) : BaseController() {

    @ApiOperation("提取中文")
    @PostMapping("/zh")
    fun extractChinese(@ApiParam("文件") @RequestPart file: MultipartFile) =
        extractText(file, ExtractorType.ZH)

    @ApiOperation("提取英文")
    @PostMapping("/en")
    fun extractEnglish(@ApiParam("文件") @RequestPart file: MultipartFile) =
        extractText(file, ExtractorType.EN)

    @ApiOperation("提取域名")
    @PostMapping("/domain")
    fun extractDomain(@ApiParam("文件") @RequestPart file: MultipartFile) =
        extractText(file, ExtractorType.Domain)

    @ApiOperation("提取类型")
    @PostMapping("/type")
    fun extractType(@ApiParam("文件") @RequestPart file: MultipartFile): JsonNode {
        val tempFile = createTempFile().apply { file.transferTo(this) }
        try {
            return objectMapper.readTree(extractorService.extractType(tempFile.path))
        } finally {
            tempFile.delete()
        }
    }

    private fun extractText(file: MultipartFile, type: ExtractorType): JsonNode {
        val tempFile = createTempFile().apply { file.transferTo(this) }
        try {
            return objectMapper.readTree(extractorService.extractText(ExtractorArgs(tempFile.path, type)))
        } finally {
            tempFile.delete()
        }
    }
}
