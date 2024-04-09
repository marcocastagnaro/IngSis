package org.example

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.arguments.optional
import org.example.sca.ScaImpl
import java.io.File
import java.util.logging.Formatter

//cli path : src/main/kotlin/CLI.kt
//./gradlew run --args "execute src/test/resources/test001.txt

class CLI : CliktCommand() {
    val execute: String by argument().help("Select execute, linter or formatter")
    val file: String by argument().help("Filepath to execute")
    val filepathJSON: String? by argument().optional().help("Filepath to execute")
//Json que se puede usar tanto para el linter como el formatter
    internal fun execute(): Output {
        val string = getFile(file)
        val tokens = executeLexing(string)
        val abstractSyntaxTrees = executeParsing(tokens)
        return executeInterpreter(abstractSyntaxTrees)
    }

    override fun run() {
        optionSelection(execute)
    }

    private fun analyze () {
        val string = getFile(file)
        val tokens = executeLexing(string)
        val abstractSyntaxTrees = executeParsing(tokens)
        val linter = ScaImpl()
        val result = linter.check(abstractSyntaxTrees)
//        return result
    }
    private fun optionSelection(option: String) {
        when (option) {
            "execute" -> echo(execute().string)
            "formatter" -> echo(formatter(filepathJSON))
            "linter" -> echo(analyze())
            else -> {
                println("Opción inválida")
            }
        }
    }

    private fun formatter(filepathJSON: String? = "src/test/resources/StandardRules.json") : String {
        val string = getFile(file)
        val tokens = executeLexing(string)
        val abstractSyntaxTrees = executeParsing(tokens)
        val format : org.example.formatter.Formatter = if (filepathJSON == null){
            org.example.formatter.Formatter("src/test/resources/StandardRules.json")
        } else {
            org.example.formatter.Formatter(filepathJSON)
        }
        val result = format.execute(abstractSyntaxTrees)
        return result;
    }

    private fun executeInterpreter(abstractSyntaxTrees: List<AbstractSyntaxTree>): Output {
        val interpreter = Interpreter()
        val result = interpreter.execute(abstractSyntaxTrees)
        return result
    }

    private fun executeLexing(string: String): List<Token> {
        val lexer = Lexer(ValueMapper())
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
fun main(args: Array<String>) = CLI().main(args)

