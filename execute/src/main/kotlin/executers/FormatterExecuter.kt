package org.example.executer

import org.example.*
import org.example.parser.Parser
import org.example.splittingStrategy.StrategyMapper
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class FormatterExecuter (){
    fun execute(
        src: java.io.InputStream,
        version: String,
        rulePath: String? = null
    ): Output {
        return exec(src, version, rulePath)
    }

    private fun exec(
        src: java.io.InputStream,
        version: String,
        rulepath: String?
    ): Output {
        try {
            return executeByLine(src, version, rulepath)
        } catch (e: java.lang.Exception) {
            throw Error(e.message)
        }
    }

    private fun executeByLine(
        src: java.io.InputStream,
        version: String,
        rulepath: String?
    ): Output {
        val answers = Output()
        val lexer = Lexer(version, StrategyMapper())
        val format: org.example.formatter.Formatter =
            if (rulepath == null) {
                org.example.formatter.Formatter("src/test/resources/StandardRules.json")
            } else {
                org.example.formatter.Formatter(rulepath)
            }
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
                    val response: String = format.execute(ast)
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
        line += " $newLine "
        return line
    }
}