package com.xhstormr.app

import kotlinx.serialization.decodeFromString
import org.json.JSONObject
import java.util.Base64
import kotlin.streams.toList

object Matcher {

    private const val COMMAND =
        """rg --json -U -f %s %s"""

    private val decoder = Base64.getDecoder()

    fun match(args: MatchArgs) = args.data
        .mapValues { (charset, patterns) ->
            patterns.mapValues { (_, data) ->
                data
                    .asSequence()
                    .map { it.trim(Char::isBoundary) }
                    .distinct()
                    .filter { it.isNotBlank() }
                    .filter { it.length > 1 }
                    // longest match first
                    .sortedByDescending { it.length }
                    .map { it.toHexString(charset) }
                    .map { App.regex(it) }
                    .chunked(5000)
                    .toList()
                    .parallelStream()
                    .map { writeTempFile(it) }
                    .flatMap { rulesFile ->
                        readProcessOutput(COMMAND.format(rulesFile, args.path))
                            .filter { JSONObject(it).getString("type") == "match" }
                            .map { json.decodeFromString<Message>(it) }
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
