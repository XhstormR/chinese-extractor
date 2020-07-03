package com.xhstormr.app

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum

class App : CliktCommand(printHelpOnEmptyArgs = true) {

    private val path by option().required()

    private val encoding by option().enum<Encoding>().required()

    private val lang by option().enum<ExtractorLang>().required()

    override fun run() {
        val extractorArgs = ExtractorArgs(lang, path, encoding)
        Extractor.extract(extractorArgs)
            .forEach { println(it) }
    }
}

fun main(args: Array<String>) = App().main(args)
