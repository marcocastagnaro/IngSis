package org.example

import java.io.File
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int


class CLI : CliktCommand() {
    val execute: String by argument().help("Select execute, linter or formatter")
    val file: String by argument().help("Filepath to execute")
    internal fun execute() : Output{
        val string = getFile(file)
        val tokens = executeLexing(string)
        val abstractSyntaxTrees = executeParsing(tokens)
        return executeInterpreter(abstractSyntaxTrees)
    }

    override fun run() {
        optionSelection(execute)
    }

    private fun optionSelection(option: String) {
        when (option) {
            "execute" ->  echo(execute().string)
            else -> {
                println("Opción inválida")
            }
        }
    }

    private fun executeInterpreter(abstractSyntaxTrees: List<AbstractSyntaxTree>) : Output {
        val interpreter = Interpreter()
        val result = interpreter.execute(abstractSyntaxTrees)
        return result;
    }

    private fun executeLexing(string: String): List<Token> {
        val lexer = Lexer2(ValueMapper())
        return lexer.execute(string)
    }

    private fun executeParsing(tokens: List<Token>): List<AbstractSyntaxTree> {
        val parser = Parser()
        return parser.execute(tokens)
    }

    fun getFile(fileName: String): String {
        val file = File(fileName)
        val stringBuilder = StringBuilder()

        try {
            file.forEachLine {
                stringBuilder.append(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }
}


