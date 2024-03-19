package org.example

class Output {
    var string = "";

    fun changeString(newString: String) {
        string = string + newString
    }
    fun print() {
        println(string)
    }
}