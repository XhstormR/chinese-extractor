package com.xhstormr.app

object Searcher {

    private const val COMMAND =
        """cmd /c rg "(?-u:[\w.]*)%s(?-u:[\w.]*)" -a -o --encoding %s %s"""

    fun search(args: SearchArgs): Set<String> {
        val (path, pattern, charset) = args

        return readProcessOutput(COMMAND.format(pattern, charset, path))
    }
}

/*
* https://docs.rs/regex/
* https://perldoc.perl.org/perlre.html
* https://perldoc.perl.org/perluniprops.html
* https://www.pcre.org/current/doc/html/pcre2syntax.html
*/
