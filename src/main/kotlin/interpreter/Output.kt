package org.example.interpreter

class Output {
    var string = "";

    fun changeString(newString: String) {
        string = string + newString
    }
    fun print() {
        println(string)
    }
}