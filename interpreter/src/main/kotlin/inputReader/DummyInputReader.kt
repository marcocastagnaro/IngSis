package org.example.inputReader

class DummyInputReader : InputReaderType {
    override fun input(name: String): String {
        return "dummy input"
    }
}
