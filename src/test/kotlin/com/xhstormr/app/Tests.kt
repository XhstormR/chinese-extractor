package com.xhstormr.app

import com.hankcs.hanlp.HanLP
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
        Dictionary.words_s
            .map { HanLP.convertToPinyinString(it, " ", false) }
            .toSet()
            .let { Files.write(Path.of("pinyin_word.txt"), it) }
    }
}
