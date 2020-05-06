package com.xhstormr.app

import java.io.InputStream
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
