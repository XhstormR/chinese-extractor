package com.xhstormr.app

import com.github.promeg.pinyinhelper.Pinyin
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.nio.file.Files
import java.nio.file.Path

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Tests {

    @BeforeAll
    fun beforeAll() {
        println("BeforeAll")
    }

    @Test
    fun word2pinyin() {
        WhiteList.words
            .map { Pinyin.toPinyin(it, "") }
            .map { it.toLowerCase() }
            .toSet()
            .let { Files.write(Path.of("pinyin_word.txt"), it) }
    }
}
