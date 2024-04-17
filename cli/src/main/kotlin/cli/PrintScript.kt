package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.types.choice
import org.example.AbstractSyntaxTree
import org.example.Interpreter
import org.example.Lexer
import org.example.Output
import org.example.Token
import org.example.parser.Parser
import org.example.sca.ScaImpl
import java.io.File

class PrintScript : CliktCommand() { // ./cli "execute" "src/main/testmlml,.
    private val version by argument(help = "version")
    private val operation by argument(help = "apply operation")
        .choice("execute", "format", "analyze")
    private val source by argument(help = "source file path")
    val filepathJSON by argument().optional().help("Filepath to execute")

// Json que se puede usar tanto para el linter como el formatter
    internal fun execute(): Output {
        val string = getFile(source)
        val tokens = executeLexing(string)
        val abstractSyntaxTrees = executeParsing(tokens)
        return executeInterpreter(abstractSyntaxTrees)
    }

    override fun run() {
        optionSelection(operation)
    }

    private fun analyze(): output.Output {
        val string = getFile(source)
        val tokens = executeLexing(string)
        val abstractSyntaxTrees = executeParsing(tokens)
        val linter = ScaImpl()
        linter.readJson(filepathJSON!!)
        val result = linter.check(abstractSyntaxTrees)
        return result
    }

    private fun optionSelection(option: String) {
        when (option) {
            "execute" -> echo(execute().string)
            "format" -> echo(formatter(filepathJSON))
            "analyze" -> analyze().getBrokenRules().forEach { echo(it) }
            else -> {
                echo("Opción inválida")
            }
        }
    }

    private fun formatter(filepathJSON: String? = "src/test/resources/StandardRules.json"): String {
        val string = getFile(source)
        val tokens = executeLexing(string)
        val abstractSyntaxTrees = executeParsing(tokens)
        val format: org.example.formatter.Formatter =
            if (filepathJSON == null) {
                org.example.formatter.Formatter("src/test/resources/StandardRules.json")
            } else {
                org.example.formatter.Formatter(filepathJSON)
            }
        val result = format.execute(abstractSyntaxTrees)
        return result
    }

    private fun executeInterpreter(abstractSyntaxTrees: List<AbstractSyntaxTree>): Output {
        val interpreter = Interpreter()
        val result = interpreter.execute(abstractSyntaxTrees)
        return result
    }

    private fun executeLexing(string: String): List<Token> {
        val lexer = Lexer(version)
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

fun main(args: Array<String>) = PrintScript().main(args)
