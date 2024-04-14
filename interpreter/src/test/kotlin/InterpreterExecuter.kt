package org.example

class InterpreterExecuter {
    private val interpreter: Interpreter = Interpreter()
    private val lexer: Lexer = Lexer(ValueMapper())
    private val parser: Parser = Parser()

    fun execute(input: String): Output {
        // Inicialización de la lista de tokens y el objeto Output
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
