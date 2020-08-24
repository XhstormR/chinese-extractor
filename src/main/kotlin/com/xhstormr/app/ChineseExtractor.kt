package com.xhstormr.app

import com.hankcs.hanlp.utility.TextUtility
import java.util.stream.Collectors

object ChineseExtractor {

    private const val COMMAND =
        """cmd /c rg "([\w.，、。？：；（） ]*)[\p{han}]{2,}([\w.，、。？：；（） ]*)" -a -o --encoding %s %s"""

    fun extract(args: ExtractorArgs) = args.type.charsets.associateWith { charset ->
        readProcessOutput(COMMAND.format(charset, args.path))
            .parallelStream()
            // 只包含常用汉字
            .filter { str -> str.filter { TextUtility.isChinese(it) }.all { WhiteList.characters.contains(it) } }
            // 至少包含一个词组
            .collect(
                Collectors.groupingByConcurrent { str ->
                    when {
                        WhiteList.local_trie.matches(str) -> TextType.Local
                        WhiteList.words_trie.matches(str) -> TextType.Words
                        WhiteList.website_trie.matches(str) -> TextType.Website
                        WhiteList.malware_trie.matches(str) -> TextType.Malware
                        WhiteList.software_trie.matches(str) -> TextType.Software
                        WhiteList.antivirus_trie.matches(str) -> TextType.Antivirus
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
