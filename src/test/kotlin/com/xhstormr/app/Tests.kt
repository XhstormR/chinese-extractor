package com.xhstormr.app

import com.hankcs.hanlp.HanLP
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
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

    @Disabled
    @Test
    fun word2pinyin() {
        Dictionary.words_s
            .map { HanLP.convertToPinyinString(it, " ", false) }
            .toSet()
            .let { Files.write(Path.of("pinyin_word.txt"), it) }
    }

    @Test
    fun trieTest1() {
        val trie = MyAhoCorasickDoubleArrayTrie(
            map = arrayOf("hers", "his", "she", "he", "World").associateBy { it },
            boundary = true,
            ignoreCase = true
        )

        Assertions.assertFalse(trie.matches("""uhers"""))
        Assertions.assertFalse(trie.matches("""whis"""))
        Assertions.assertFalse(trie.matches("""hisw"""))
        Assertions.assertFalse(trie.matches("""whis """))
        Assertions.assertFalse(trie.matches(""" hisw"""))

        Assertions.assertTrue(trie.matches("""she"""))
        Assertions.assertTrue(trie.matches("""she """))
        Assertions.assertTrue(trie.matches(""" she"""))
        Assertions.assertTrue(trie.matches(""" she """))
        Assertions.assertTrue(trie.matches("""he rs"""))
        Assertions.assertTrue(trie.matches("""u hers"""))
        Assertions.assertTrue(trie.matches("""u hers """))

        Assertions.assertTrue(trie.matches("""hers"""))
        Assertions.assertTrue(trie.matches(""""hers""""))
        Assertions.assertTrue(trie.matches("""'hers'"""))
        Assertions.assertTrue(trie.matches("""'hers,"""))
        Assertions.assertTrue(trie.matches(""" hers,"""))

        Assertions.assertTrue(trie.matches("""World"""))
        Assertions.assertTrue(trie.matches("""WORLD"""))
        Assertions.assertTrue(trie.matches("""world"""))
    }

    @Test
    fun trieTest2() {
        val trie = MyAhoCorasickDoubleArrayTrie(
            map = arrayOf("hers", "his", "she", "he", "World").associateBy { it },
            boundary = false,
            ignoreCase = false
        )

        Assertions.assertTrue(trie.matches("""uhers"""))
        Assertions.assertTrue(trie.matches("""whis"""))
        Assertions.assertTrue(trie.matches("""hisw"""))
        Assertions.assertTrue(trie.matches("""whis """))
        Assertions.assertTrue(trie.matches(""" hisw"""))

        Assertions.assertTrue(trie.matches("""she"""))
        Assertions.assertTrue(trie.matches("""she """))
        Assertions.assertTrue(trie.matches(""" she"""))
        Assertions.assertTrue(trie.matches(""" she """))
        Assertions.assertTrue(trie.matches("""he rs"""))
        Assertions.assertTrue(trie.matches("""u hers"""))
        Assertions.assertTrue(trie.matches("""u hers """))

        Assertions.assertTrue(trie.matches("""hers"""))
        Assertions.assertTrue(trie.matches(""""hers""""))
        Assertions.assertTrue(trie.matches("""'hers'"""))
        Assertions.assertTrue(trie.matches("""'hers,"""))
        Assertions.assertTrue(trie.matches(""" hers,"""))

        Assertions.assertTrue(trie.matches("""World"""))
        Assertions.assertFalse(trie.matches("""WORLD"""))
        Assertions.assertFalse(trie.matches("""world"""))
    }
}
