package org.example.executer
import org.example.AbstractSyntaxTree
import org.example.Interpreter
import org.example.Lexer
import org.example.Output
import org.example.Token
import org.example.inputReader.DummyInputReader
import org.example.inputReader.InputReaderType
import org.example.parser.Parser
import org.example.splittingStrategy.StrategyMapper
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.Arrays

class Executer() {
    fun execute(
        src: java.io.InputStream,
        version: String,
        input: String? = null
    ): Output {
        return exec(src, version, input)
    }

    private fun exec(
        src: java.io.InputStream,
        version: String,
        input: String?
    ): Output {
        try {
            return executeByLine(src, version, input)
        } catch (e: java.lang.Exception) {
            throw Error(e.message)
        }
    }

    private fun executeByLine(
        src: java.io.InputStream,
        version: String,
        input: String?
    ): Output {
        val answers = Output()
        val lexer = Lexer(version, StrategyMapper())

        val newInput = if (input != null) InputReader(input) else DummyInputReader()
        val interpreter = Interpreter(newInput)

        val reader = BufferedReader(InputStreamReader(src))
        try {
            reader.use {
                var line: String? = reader.readLine()
                while (line != null) {
                    var lineContent = line
                    if (lineContent.contains("if")) {
                        lineContent = handleIf(lineContent, reader)
                    }
                    val tokens: List<Token> = lexer.execute(lineContent)
                    val ast: List<AbstractSyntaxTree> = Parser().execute(tokens)
                    val response: String = interpreter.execute(ast).string
                    answers.buildOutput(splitByLinesAndPrintResponse(response).string)

                    line = reader.readLine() // Leer la siguiente línea
                }
            }
        } catch (e: Exception) {
            throw Error(e.message)
        }
        return answers
    }

    private fun splitByLinesAndPrintResponse(response: String): Output {
        val splitResponse: List<String> =
            Arrays.stream<String>(
                response.split("\n".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray(),
            ).toList()
        val answer: Output = Output()
        splitResponse.forEach(
            java.util.function.Consumer<String> { self: String ->
                if (!self.isBlank()) {
                    answer.buildOutput(self)
                    answer.buildOutput("\n")

                }
            },
        )
        return answer
    }

    @kotlin.Throws(IOException::class)
    private fun handleIf(
        string: String,
        reader: BufferedReader,
    ): String {
        var line = string
        while (!line.contains("}")) {
            line += reader.readLine()
        }
        var newLine: String = reader.readLine()

        if (newLine.contains("else")) {
            while (!newLine.contains("}")) {
                newLine += reader.readLine()
            }
        }
        line += " $newLine }"
        return line
    }
}

class InputReader(val returnValue: String): InputReaderType{
    override fun input(name: String): String {
        println(name)
        return returnValue
    }
}
