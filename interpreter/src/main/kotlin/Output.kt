package org.example

import org.example.utils.ClearCommas

class Output {
    var string = ""

    fun buildOutput(newString: String) {
        string += ClearCommas().clearCommas(newString)
    }

    fun setOutput(newString: String) {
        string = newString
    }

    fun print() {
        println(string)
    }

    fun removeLastNewLine() {
        if (string.endsWith("\n")) {
            string = string.dropLast(1)
        }
    }
}
