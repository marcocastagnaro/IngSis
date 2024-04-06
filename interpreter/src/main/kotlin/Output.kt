package org.example

class Output {
    var string = ""

    fun buildOutput(newString: String) {
        string += newString
    }

    fun print() {
        println(string)
    }
}
