package com.xhstormr.app

import com.squareup.moshi.Moshi
import java.io.InputStream
import java.nio.charset.Charset
import java.nio.file.Path

fun getSystemResource(name: String): Path =
    Path.of(ClassLoader.getSystemResource(name).toURI())

fun getSystemResourceAsStream(name: String): InputStream =
    ClassLoader.getSystemResourceAsStream(name)!!

fun readProcessOutput(command: String) = Runtime.getRuntime()
    .exec(command)
    .inputStream
    .bufferedReader()
    .useLines { it.toSet() }

fun String.toHexString(charset: Charset) = this.toByteArray(charset)
    .joinToString("""\x""", """\x""") { "%02x".format(it) }

inline fun <reified T> clazz() = T::class.java

val moshi: Moshi = Moshi.Builder()
    .add(CharsetAdapter)
    .build()
