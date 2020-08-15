package com.xhstormr.app

import com.github.promeg.pinyinhelper.Pinyin
import java.util.stream.Collectors

object ChineseExtractor {

    private const val COMMAND =
        """cmd /c rg "([\w.，、。？：；（） ]*)[\p{han}]{2,}([\w.，、。？：；（） ]*)" -a -o --encoding %s %s"""

    fun extract(args: ExtractorArgs) = args.type.charsets.associateWith { charset ->
        readProcessOutput(COMMAND.format(charset, args.path))
            .parallelStream()
            // 只包含常用汉字
            .filter { str -> str.filter { Pinyin.isChinese(it) }.all { WhiteList.characters.contains(it) } }
            // 至少包含一个词组
            .collect(
                Collectors.groupingByConcurrent<String, TextType> { str ->
                    when {
                        WhiteList.words.any { str.contains(it, true) } -> TextType.Words
                        WhiteList.website.any { str.contains(it, true) } -> TextType.Website
                        WhiteList.malware.any { str.contains(it, true) } -> TextType.Malware
                        WhiteList.antivirus.any { str.contains(it, true) } -> TextType.Antivirus
                        else -> TextType.None
                    }
                }
            )
            .apply { remove(TextType.None) }
    }
}

/*
* iconv -f GBK -t UTF-8 -c 123.dmp | rg "[\p{han}]{2,}" -a -o | busybox sort -u
*/

/*
* https://docs.rs/regex/
* https://perldoc.perl.org/perlre.html
* https://perldoc.perl.org/perluniprops.html
* https://www.pcre.org/current/doc/html/pcre2syntax.html
*/
