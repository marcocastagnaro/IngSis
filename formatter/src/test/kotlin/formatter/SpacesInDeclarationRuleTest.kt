package org.example.formatter

import org.example.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SpacesInDeclarationRuleTest {
    @Test
    fun `test 001 simple two points testing only space after`() {
        val declaration = "let x : number = 8;"
        val tokens = Lexer(ValueMapper()).execute(declaration)
        assertEquals("let", tokens[0].getValue())
        assertEquals("x", tokens[1].getValue())
        assertEquals(":", tokens[2].getValue())
        assertEquals("number", tokens[3].getValue())
        assertEquals("=", tokens[4].getValue())
        assertEquals(7, tokens.size)
        val trees = Parser().execute(tokens)
        assertEquals(1, trees.size)
        val formatter = Formatter(listOf(SpacesInDeclarationRule()))
        val result = formatter.execute(trees)
         assertEquals("let x: number = 8;\n", result)
    }

    @Test
    fun `test 002 space only after but with manual tokens`() {
        val tokens =
            listOf(
                Token(Types.KEYWORD, "let", Position(1, 1), Position(1, 3)),
                Token(Types.IDENTIFIER, "x", Position(1, 5), Position(1, 5)),
                Token(Types.DECLARATOR, ":", Position(1, 6), Position(1, 6)),
                Token(Types.DATA_TYPE, "number", Position(1, 7), Position(1, 12)),
                Token(Types.ASSIGNATION, "=", Position(1, 14), Position(1, 14)),
                Token(Types.LITERAL, "8", Position(1, 16), Position(1, 16)),
                Token(Types.PUNCTUATOR, ";", Position(1, 17), Position(1, 17)),
            )
        val tree = (
            CompositeAbstractSyntaxTree(
                tokens[4],
                left =
                    CompositeAbstractSyntaxTree(
                        tokens[0],
                        right =
                            CompositeAbstractSyntaxTree(
                                tokens[2],
                                left = Leaf(tokens[1]),
                                right = Leaf(tokens[3]),
                            ),
                    ),
                right = Leaf(tokens[5]),
            )
        )
        val trees = listOf(tree)
        val formatter = Formatter(listOf(SpacesInDeclarationRule()))
        val result = formatter.execute(trees)
        assertEquals("let x: number = 8;\n", result)
    }

    @Test
    fun `test 003 no spaces nor before nor after`() {
        val tokens =
            listOf(
                Token(Types.KEYWORD, "let", Position(1, 1), Position(1, 3)),
                Token(Types.IDENTIFIER, "x", Position(1, 5), Position(1, 5)),
                Token(Types.DECLARATOR, ":", Position(1, 6), Position(1, 7)),
                Token(Types.DATA_TYPE, "number", Position(1, 10), Position(1, 12)),
                Token(Types.ASSIGNATION, "=", Position(1, 14), Position(1, 14)),
                Token(Types.LITERAL, "8", Position(1, 16), Position(1, 16)),
                Token(Types.PUNCTUATOR, ";", Position(1, 17), Position(1, 17)),
            )
        val tree = (
            CompositeAbstractSyntaxTree(
                tokens[4],
                left =
                    CompositeAbstractSyntaxTree(
                        tokens[0],
                        right =
                            CompositeAbstractSyntaxTree(
                                tokens[2],
                                left = Leaf(tokens[1]),
                                right = Leaf(tokens[3]),
                            ),
                    ),
                right = Leaf(tokens[5]),
            )
        )
        val trees = listOf(tree)
        val formatter = Formatter(listOf(SpacesInDeclarationRule(spaceBefore = true, spaceAfter = false)))
        val result = formatter.execute(trees)
        assertEquals("let x :number = 8;\n", result)
    }

    @Test
    fun `test 004 spaces before and after`() {
        val tokens =
            listOf(
                Token(Types.KEYWORD, "let", Position(1, 1), Position(1, 3)),
                Token(Types.IDENTIFIER, "x", Position(1, 5), Position(1, 5)),
                Token(Types.DECLARATOR, ":", Position(1, 6), Position(1, 6)),
                Token(Types.DATA_TYPE, "number", Position(1, 8), Position(1, 12)),
                Token(Types.ASSIGNATION, "=", Position(1, 14), Position(1, 14)),
                Token(Types.LITERAL, "8", Position(1, 16), Position(1, 16)),
                Token(Types.PUNCTUATOR, ";", Position(1, 17), Position(1, 17)),
            )
        val tree = (
            CompositeAbstractSyntaxTree(
                tokens[4],
                left =
                    CompositeAbstractSyntaxTree(
                        tokens[0],
                        right =
                            CompositeAbstractSyntaxTree(
                                tokens[2],
                                left = Leaf(tokens[1]),
                                right = Leaf(tokens[3]),
                            ),
                    ),
                right = Leaf(tokens[5]),
            )
        )
        val trees = listOf(tree)
        val formatter = Formatter(listOf(SpacesInDeclarationRule(spaceBefore = true, spaceAfter = true)))
        val result = formatter.execute(trees)
        assertEquals("let x : number = 8;\n", result)
    }
}
