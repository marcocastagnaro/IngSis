package org.example

data class VariableToken(val value: String, val type: TokenType)

enum class TokenType {
    NUMBER,
    STRING,
    PRINT,
}
