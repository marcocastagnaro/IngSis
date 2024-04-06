import java.io.File
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import org.example.AbstractSyntaxTree
import org.example.Lexer2
import org.example.Token
import org.example.ValueMapper
import org.example.Interpreter
import org.example.Parser

class CLI : CliktCommand() {
    private val fileName by argument(help = "src/main/resources/test.txt")
    override fun  run () { //El filename es el path
        val string = getFile(fileName)
        val tokens =executeLexing(string)
        val abstractSyntaxTrees = executeParsing(tokens)
        executeInterpreter(abstractSyntaxTrees)

    }
    private fun executeInterpreter (abstractSyntaxTrees : List<AbstractSyntaxTree>) {
        val interpreter = Interpreter()
        interpreter.execute(abstractSyntaxTrees)
    }
    private fun executeLexing (string : String ) : List<Token> {
        val lexer = Lexer2(ValueMapper())
        return lexer.execute(string)
    }
    private fun executeParsing (tokens : List<Token>) : List<AbstractSyntaxTree> {
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