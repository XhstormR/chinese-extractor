package com.xhstormr.app

fun main(args: Array<String>) {

    if (args.size != 3) {
        println("Usage: text-extractor <type> <path> <lang>")
        return
    }

    val txtPath = createTempFile()
        .apply { deleteOnExit() }
        .path

    val converterArgs = ConverterArgs(ConverterType.valueOf(args[0].toUpperCase()), args[1], txtPath)
    Converter.convert(converterArgs)

    val extractorArgs = ExtractorArgs(ExtractorLang.valueOf(args[2].toUpperCase()), txtPath)
    Extractor.extract(extractorArgs)
        .forEach { println(it) }
}
