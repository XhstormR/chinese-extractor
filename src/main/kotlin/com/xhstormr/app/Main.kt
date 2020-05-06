package com.xhstormr.app

fun main(args: Array<String>) {

    if (args.size != 2) {
        println("Usage: chinese-extractor <type> <path>")
        return
    }

    val txtPath = createTempFile()
        .apply { deleteOnExit() }
        .path

    val converterArgs = ConverterArgs(ConverterType.valueOf(args[0].toUpperCase()), args[1], txtPath)
    Converter.convert(converterArgs)

    val extractorArgs = ExtractorArgs(txtPath)
    Extractor.extract(extractorArgs)
        // 只包含常用汉字
        .filter { str -> str.all { WhiteList.characters.contains(it) } }
        // 至少包含一个词组
        .filter { str -> WhiteList.words.any { str.contains(it) } }
        .map { SearchArgs(it, txtPath) }
        .flatMap { Searcher.search(it) }
        .forEach { println(it) }
}
