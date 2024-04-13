package org.example.inputReader

class ReadInputFromTerminal : InputReaderType {
    override fun getInput(): String {
        return readln()
    }
}
