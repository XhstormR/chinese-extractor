package com.xhstormr.app

import java.nio.charset.Charset

object AllExtractor {

    fun extract(args: ExtractorArgs): Map<Charset, Map<TextType, Set<String>>> = ExtractorType.values()
        .filterNot { it == ExtractorType.All }
        .map { args.copy(type = it) }
        .map { Extractor.extract(it) }
        .flatMap { it.entries }
        .groupBy({ it.key }, { it.value.entries })
        .mapValues { (_, v) ->
            v
                .flatten()
                .groupBy({ it.key }, { it.value })
                .mapValues { it.value.flatten().toSet() }
        }
}
