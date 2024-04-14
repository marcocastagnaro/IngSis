package org.example

import org.example.inputReader.InputReaderType

class InterpreterExecuter(provider: InputReaderType) {
    private val interpreter: Interpreter = Interpreter(provider)
    private val lexer: Lexer = Lexer(ValueMapper())
    private val parser: Parser = Parser()

    fun execute(input: String): Output {
        val listOfTokens = ArrayList<Token>()
        val output = Output()

        val segments = input.split(";")

        for (segment in segments) {
            if (segment.isNotBlank()) {
                val tokens = lexer.execute(segment)
                listOfTokens.addAll(tokens)
            }
        }

        val trees = parser.execute(listOfTokens)

        val interpreterResult = interpreter.execute(trees)
        output.buildOutput(interpreterResult.string)

        return output
    }
}
