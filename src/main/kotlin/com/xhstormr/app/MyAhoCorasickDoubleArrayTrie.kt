package com.xhstormr.app

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie
import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie.IHitCancellable

class MyAhoCorasickDoubleArrayTrie(
    map: Map<String, String>,
    private val boundary: Boolean,
    private val ignoreCase: Boolean,
) : AhoCorasickDoubleArrayTrie<String>() {

    init {
        if (ignoreCase) build(map.mapKeys { (k, _) -> k.toLowerCase() })
        else build(map)
    }

    override fun matches(text: String): Boolean {
        var isMatched = false

        parseText(
            text,
            IHitCancellable { begin, end, _ ->
                var isWord = true
                if (boundary && begin != 0 && text[begin - 1] != ' ') isWord = false
                if (boundary && end != text.length && text[end] != ' ') isWord = false

                if (!boundary || isWord) {
                    isMatched = true
                    false
                } else {
                    true
                }
            }
        )

        return isMatched
    }

    override fun transitionWithRoot(nodePos: Int, c: Char) =
        if (ignoreCase) super.transitionWithRoot(nodePos, c.toLowerCase())
        else super.transitionWithRoot(nodePos, c)
}
