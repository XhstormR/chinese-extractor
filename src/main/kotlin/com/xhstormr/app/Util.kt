package com.xhstormr.app

import com.squareup.moshi.Moshi
import java.io.File
import java.io.InputStream
import java.io.SequenceInputStream
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.util.Collections

fun getSystemResource(name: String): Path =
    Path.of(ClassLoader.getSystemResource(name).toURI())

fun getSystemResourceAsStream(name: String): InputStream =
    ClassLoader.getSystemResourceAsStream(name)!!

fun getFileInputStream(pathname: String) =
    File(pathname).takeIf { it.exists() }?.inputStream()

fun writeTempFile(text: String) = createTempFile()
    .apply { deleteOnExit() }
    .apply { writeText(text) }

fun writeTempFile(texts: Collection<String>) = createTempFile()
    .apply { deleteOnExit() }
    .apply { Files.write(toPath(), texts) }

fun readProcessOutput(command: String) = ProcessBuilder(command.split(" "))
    .redirectErrorStream(true)
    .start()
    .inputStream
    .bufferedReader()
    .useLines { it.toSet() }

fun String.toHexString(charset: Charset) = this.toByteArray(charset)
    .joinToString("""\x""", """\x""") { "%02x".format(it) }

inline fun <reified T> clazz() = T::class.java

operator fun InputStream.plus(inputStream: InputStream) =
    SequenceInputStream(Collections.enumeration(listOf(this, inputStream)))

val moshi: Moshi = Moshi.Builder()
    .add(CharsetAdapter)
    .build()
