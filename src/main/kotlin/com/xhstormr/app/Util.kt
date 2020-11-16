package com.xhstormr.app

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import java.io.File
import java.io.InputStream
import java.io.SequenceInputStream
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.util.Collections
import java.util.regex.Pattern

fun getCurrentJar() =
    File(clazz<App>().protectionDomain.codeSource.location.toURI())

fun getSystemResource(name: String): Path =
    Path.of(ClassLoader.getSystemResource(name).toURI())

fun getSystemResourceAsStream(name: String): InputStream =
    ClassLoader.getSystemResourceAsStream(name)!!

fun getFileInputStream(pathname: String) =
    getFileInputStream(File(pathname))

fun getFileInputStream(file: File) =
    file.takeIf { it.exists() }?.inputStream()

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

fun String.toHexString(charset: Charset) = this
    .toByteArray(charset)
    .joinToString("") { """\x%02x""".format(it) }

private val BOUNDARY_PREDICATE = Pattern.compile("""[^\w\u4E00-\u9FA5]""").asMatchPredicate()

private val CHINESE_PREDICATE = Pattern.compile("""[\u4E00-\u9FA5]""").asMatchPredicate()

fun Char.isBoundary() = BOUNDARY_PREDICATE.test(this.toString())

fun Char.isChinese() = CHINESE_PREDICATE.test(this.toString())

inline fun <reified T> clazz() = T::class.java

operator fun InputStream.plus(inputStream: InputStream) =
    SequenceInputStream(Collections.enumeration(listOf(this, inputStream)))

val json = Json {
    serializersModule = SerializersModule { contextual(CharsetSerializer) }
}
