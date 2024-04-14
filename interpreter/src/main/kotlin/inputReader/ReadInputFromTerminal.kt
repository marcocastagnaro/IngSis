package org.example.inputReader

class ReadInputFromTerminal : InputReaderType {
    override fun input(): String {
        return readln()
    }
}
