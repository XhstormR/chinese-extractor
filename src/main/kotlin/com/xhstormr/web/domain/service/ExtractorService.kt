package com.xhstormr.web.domain.service

import com.xhstormr.web.app.config.ExtractorProperties
import com.xhstormr.web.domain.model.ExtractorArgs
import com.xhstormr.web.domain.util.readProcessOutput
import org.springframework.stereotype.Service

@Service
class ExtractorService(
    private val extractorProperties: ExtractorProperties
) {

    companion object {
        private const val TEXT_COMMAND =
            """java -jar %s --path %s --type %s"""

        private const val DIEC_COMMAND =
            """%s -d -j %s"""
    }

    fun extractText(args: ExtractorArgs) =
        readProcessOutput(TEXT_COMMAND.format(extractorProperties.textBin, args.path, args.type))

    fun extractType(path: String) =
        readProcessOutput(DIEC_COMMAND.format(extractorProperties.diecBin, path))
}
