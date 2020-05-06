package com.xhstormr.app

object Searcher {

    private const val COMMAND =
        """cmd /c rg "[0-9A-Z_a-z]*%s[0-9A-Z_a-z]*" -a -o %s"""

    fun search(args: SearchArgs): Set<String> {
        val (pattern, path) = args

        return readProcessOutput(COMMAND.format(pattern, path))
    }
}

/*
* https://perldoc.perl.org/perluniprops.html
*/
