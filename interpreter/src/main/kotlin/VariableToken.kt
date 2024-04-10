package org.example

import java.sql.Types

data class VariableToken(val value: String, val type: TokenType)

enum class TokenType {
    NUMBER,
    STRING,
}