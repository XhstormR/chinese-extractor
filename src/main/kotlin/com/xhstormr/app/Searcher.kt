package com.xhstormr.app

object Searcher {

    private const val COMMAND =
        """cmd /c rg "(?-u:[\w.]*)%s(?-u:[\w.]*)" -a -o %s"""

    fun search(args: SearchArgs): Set<String> {
        val (pattern, path) = args

        return readProcessOutput(COMMAND.format(pattern, path))
    }
}

/*
* https://docs.rs/regex/
* https://perldoc.perl.org/perlre.html
* https://perldoc.perl.org/perluniprops.html
* https://www.pcre.org/current/doc/html/pcre2syntax.html
*/
