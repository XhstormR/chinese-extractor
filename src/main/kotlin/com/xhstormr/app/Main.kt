package com.xhstormr.app

fun main(args: Array<String>) {

    if (args.size != 2) {
        println("Usage: chinese-extractor <type> <path>")
        return
    }

    val extractorArgs = ExtractorArgs(ExtractorType.valueOf(args[0].toUpperCase()), args[1])

    Extractor.extract(extractorArgs)
        // 只包含常用汉字
        .filter { str -> str.all { WhiteList.characters.contains(it) } }
        // 至少包含一个词组
        .filter { str -> WhiteList.words.any { str.contains(it) } }
        .forEach { println(it) }
}
