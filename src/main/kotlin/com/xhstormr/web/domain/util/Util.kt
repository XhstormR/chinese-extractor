package com.xhstormr.web.domain.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import java.io.File
import java.lang.invoke.MethodHandles
import kotlin.math.min

/**
 * 内联：for convenience
 */
inline fun getLogger(): Logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

inline fun <reified T> clazz() = T::class.java

fun readProcessOutput(command: String) = ProcessBuilder(command.split(" "))
    .redirectErrorStream(true)
    .start()
    .inputStream
    .bufferedReader(if (isWindows()) charset("GBK") else Charsets.UTF_8)
    .use { it.readText() }

fun isWindows() = File.separatorChar == '\\'

fun <T> List<T>.applyPagination(pageable: Pageable) = subList(
    min(size, pageable.offset.toInt()),
    min(size, (pageable.offset + pageable.pageSize).toInt())
)
