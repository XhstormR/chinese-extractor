package com.xhstormr.app

object Extractor {

    fun extract(args: ExtractorArgs): List<String> {
        val (lang, path) = args

        return when (lang) {
            ExtractorLang.ZH -> ChineseExtractor.extract(path)
            ExtractorLang.EN -> EnglishExtractor.extract(path)
        }
    }
}
