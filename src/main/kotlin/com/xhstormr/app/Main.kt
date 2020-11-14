package com.xhstormr.app

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import kotlinx.serialization.encodeToString

object App : CliktCommand(printHelpOnEmptyArgs = true) {

    private const val RULE_TEMPLATE =
        """(%s:%s)"""

    private const val BOUNDARY_TEMPLATE =
        """\b%s\b"""

    private val path by option().required()

    private val type by option().enum<Extractor>().required()

    val boundary by option().flag()

    val ignoreCase by option().flag()

    override fun run() {
        type.extract(path)
            .let { Matcher.match(MatchArgs(path, it)) }
            .let { println(json.encodeToString(it)) }
    }

    fun regex(pattern: String) = pattern
        .let { if (boundary) BOUNDARY_TEMPLATE.format(it) else it }
        .let { if (ignoreCase) RULE_TEMPLATE.format("?i-u", it) else RULE_TEMPLATE.format("?-u", it) }
}

fun main(args: Array<String>) = App.main(args)
