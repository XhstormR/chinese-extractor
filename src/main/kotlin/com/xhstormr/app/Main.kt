package com.xhstormr.app

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import com.squareup.moshi.Types
import java.nio.charset.Charset

object App : CliktCommand(printHelpOnEmptyArgs = true) {

    private val path by option().required()

    private val lang by option().enum<ExtractorLang>().required()

    override fun run() {
        val type = Types.newParameterizedType(clazz<Map<*, *>>(), clazz<Charset>(), clazz<List<*>>(), clazz<Sample>())
        val jsonAdapter = moshi.adapter<Map<Charset, List<Sample>>>(type)

        Extractor.extract(ExtractorArgs(path, lang))
            .let { Matcher.match(MatchArgs(path, it)) }
            .let { println(jsonAdapter.toJson(it)) }
    }
}

fun main(args: Array<String>) = App.main(args)
