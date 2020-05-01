package com.xhstormr.app

import java.io.InputStream
import java.nio.file.Path

fun getSystemResource(name: String): Path =
    Path.of(ClassLoader.getSystemResource(name).toURI())

fun getSystemResourceAsStream(name: String): InputStream =
    ClassLoader.getSystemResourceAsStream(name)!!
