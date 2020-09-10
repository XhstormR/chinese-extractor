package com.xhstormr.web.domain.service

import com.xhstormr.web.app.config.ExtractorProperties
import com.xhstormr.web.domain.model.TextType
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

    fun saveContent(textType: TextType, content: String) =
        lexiconDir.resolve(textType.lexicon).writeText(content + System.lineSeparator())
}
