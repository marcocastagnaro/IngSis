import org.example.AbstractSyntaxTree
import org.example.Token.Token

class Formatter(private val formatRules: List<FormatRule>) {
    fun execute(input: AbstractSyntaxTree): String {
        val tokens = ParseTreeToTokens().parseToString(input)
        return giveFormat(tokens)
        // TODO falta agregar un lector de espacios y lineas
    }

    private fun giveFormat(tokens: List<Token>): String {
        var formattedTokens = tokens
        formatRules.forEach {
            formattedTokens = it.applyRule(formattedTokens)
        }
        return formattedTokens.joinToString("") { it.getValue() }
    }
}
