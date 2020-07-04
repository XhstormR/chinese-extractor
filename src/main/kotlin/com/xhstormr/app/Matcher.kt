package com.xhstormr.app

import java.nio.charset.Charset
import java.nio.file.Files
import java.util.Base64
import org.json.JSONObject

object Matcher {

    private const val RULE_TEMPLATE =
        """(?-u:%s)"""

    private const val COMMAND =
        """cmd /c rg --json -f %s %s"""

    fun match(args: MatchArgs): Map<Charset, List<Sample>> {
        val (path, data) = args

        val rulesFile = createTempFile()
            .apply { deleteOnExit() }
            .toPath()

        val decoder = Base64.getDecoder()
        val jsonAdapter = moshi.adapter(clazz<Message>())

        return data.mapValues { (charset, patterns) ->
            val rules = patterns
                .map { it.toHexString(charset) }
                .map { RULE_TEMPLATE.format(it) }

            Files.write(rulesFile, rules)

            readProcessOutput(COMMAND.format(rulesFile, path))
                .filter { JSONObject(it).getString("type") == "match" }
                .mapNotNull { jsonAdapter.fromJson(it) }
                .map { it.data }
                .flatMap { data ->
                    data.submatches.map {
                        val text = it.match.text ?: String(decoder.decode(it.match.bytes), charset)
                        val offset = data.absoluteOffset + it.start
                        val length = it.end - it.start
                        Sample(text, offset, length)
                    }
                }
        }
    }
}