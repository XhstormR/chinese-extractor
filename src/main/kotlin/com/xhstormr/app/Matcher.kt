package com.xhstormr.app

import org.json.JSONObject
import java.util.Base64
import kotlin.streams.toList

object Matcher {

    private const val RULE_TEMPLATE =
        """(?-u:%s)"""

    private const val COMMAND =
        """cmd /c rg --json -U -f %s %s"""

    private val decoder = Base64.getDecoder()

    private val jsonAdapter = moshi.adapter(clazz<Message>())

    fun match(args: MatchArgs) = args.data
        .mapValues { (charset, patterns) ->
            patterns.mapValues { (_, data) ->
                data
                    // longest match first
                    .sortedByDescending { it.length }
                    .map { it.toHexString(charset) }
                    .map { RULE_TEMPLATE.format(it) }
                    .chunked(5000)
                    .parallelStream()
                    .map { writeTempFile(it) }
                    .flatMap { rulesFile ->
                        readProcessOutput(COMMAND.format(rulesFile, args.path))
                            .filter { JSONObject(it).getString("type") == "match" }
                            .mapNotNull { jsonAdapter.fromJson(it) }
                            .map { it.data }
                            .flatMap { data ->
                                data.submatches.map {
                                    val text = it.match.text?.toByteArray(Charsets.UTF_8)?.toString(charset)
                                        ?: decoder.decode(it.match.bytes).toString(charset)
                                    val offset = data.absoluteOffset + it.start
                                    val length = it.end - it.start
                                    Sample(text, offset, length)
                                }
                            }.stream()
                    }.toList()
            }
        }
}
