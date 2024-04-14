package org.example.strategies

data class VariableToken(val value: String, val type: TokenType)

enum class TokenType {
    NUMBER,
    STRING,
    PRINT,
}
