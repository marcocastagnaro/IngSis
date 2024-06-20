package org.example

import org.example.utils.ClearCommas

class Output {
    private var _string = ""

    val string: String
        get() = _string

    fun buildOutput(newString: String) {
        _string += ClearCommas().clearCommas(newString)
    }

    fun setOutput(newString: String) {
        _string = newString
    }

    fun print() {
        println(string)
    }

    fun removeLastNewLine() {
        if (string.endsWith("\n")) {
            _string = string.dropLast(1)
        }
    }
}
