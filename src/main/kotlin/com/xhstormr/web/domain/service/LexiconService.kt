package com.xhstormr.web.domain.service

import com.xhstormr.web.app.config.ExtractorProperties
import com.xhstormr.web.domain.model.LexiconLinePredicateRequest
import com.xhstormr.web.domain.model.TextType
import com.xhstormr.web.domain.util.applyPagination
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Service

@Service
class LexiconService(
    extractorProperties: ExtractorProperties
) {

    private val lexiconDir = extractorProperties.textBin.resolveSibling("lexicon").toFile()
        .apply { mkdirs() }

    fun getContent(textType: TextType) =
        lexiconDir.resolve(textType.lexicon).takeIf { it.exists() }
            ?.run { readText() }

    private fun getListContent(textType: TextType) =
        lexiconDir.resolve(textType.lexicon).apply { createNewFile() }
            .run { readLines().toMutableSet() }

    fun saveContent(textType: TextType, content: String) =
        lexiconDir.resolve(textType.lexicon).writeText(content + System.lineSeparator())

    fun getPageContent(textType: TextType, pageable: Pageable, predicate: LexiconLinePredicateRequest): Page<String> {
        val list = getListContent(textType)
            .filter { predicate.line?.let { line -> it.contains(line) } ?: true }
        val content = list.applyPagination(pageable)

        return PageableExecutionUtils.getPage(content, pageable) { list.size.toLong() }
    }

    fun addLines(textType: TextType, lines: List<String>) =
        getListContent(textType)
            .apply { addAll(lines) }
            .let { saveContent(textType, it.joinToString(System.lineSeparator())) }

    fun deleteLines(textType: TextType, lines: List<String>) =
        getListContent(textType)
            .apply { removeAll(lines) }
            .let { saveContent(textType, it.joinToString(System.lineSeparator())) }
}
