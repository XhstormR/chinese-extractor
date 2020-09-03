package com.xhstormr.app

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import com.squareup.moshi.Types
import java.nio.charset.Charset

object App : CliktCommand(printHelpOnEmptyArgs = true) {

    private val path by option().required()

    private val type by option().enum<Extractor>().required()

    override fun run() {
        val parameterizedType =
            Types.newParameterizedType(clazz<Map<*, *>>(), clazz<Charset>(), clazz<Map<*, *>>(), clazz<TextType>(), clazz<List<*>>(), clazz<Sample>())
        val jsonAdapter = moshi.adapter<Map<Charset, Map<TextType, List<Sample>>>>(parameterizedType)

        type.extract(path)
            .let { Matcher.match(MatchArgs(path, it)) }
            .let { println(jsonAdapter.toJson(it)) }
    }
}

fun main(args: Array<String>) = App.main(args)