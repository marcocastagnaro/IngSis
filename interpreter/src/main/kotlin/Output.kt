package org.example

import org.example.utils.ClearCommas

class Output {
    var string = ""

    fun buildOutput(newString: String) {
        string += ClearCommas().clearCommas(newString)
    }

    fun print() {
        println(string)
    }
}
