package org.example.inputReader

class ReadInputFromTerminal : InputReaderType {
    override fun input(name: String): String {
        return readln()
    }
}
