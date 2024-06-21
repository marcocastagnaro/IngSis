package org.example.executer

import org.example.AbstractSyntaxTree
import org.example.Lexer
import org.example.Output
import org.example.Token
import org.example.parser.Parser
import org.example.splittingStrategy.StrategyMapper
import org.example.staticCodeeAnalyzer.SCAOutput
import org.example.staticCodeeAnalyzer.ScaImpl
import org.example.staticCodeeAnalyzer.ScaVersion
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class LinterExecuter() {
    fun execute(
        src: java.io.InputStream,
        version: String,
        rulePath: String? = null
    ): MutableList<SCAOutput> {
        return exec(src, version, rulePath)
    }

    private fun exec(
        src: java.io.InputStream,
        version: String,
        rulepath: String?
    ): MutableList<SCAOutput> {
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
    ): MutableList<SCAOutput> {
        val response: MutableList<SCAOutput> = mutableListOf()
        val lexer = Lexer(version, StrategyMapper())
        val linter = ScaImpl(ScaVersion.VERSION_1_1)
        linter.readJson(rulepath!!)
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
                    response.add(linter.check(ast))
                    line = reader.readLine() // Leer la siguiente línea
                }
            }
        } catch (e: Exception) {
            throw Error(e.message)
        }
        return response
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