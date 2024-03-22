package org.example

import org.example.Token.Position
import org.example.Token.Token

class Lexer(private var map: ValueMapper) {
    fun execute(string: String): List<Token> {
        val rows = splitRows(string)
        val tokens = ArrayList<SplitToken>()
        for ((index, row) in rows.withIndex()) {
            tokens.addAll(splitRow(row, index))
        }
        return map.assigningTypesToTokenValues(tokens)
    }

    private fun splitRows(string: String): List<String> {
        return string.split("\n")
    }

    private fun splitRow(
        string: String,
        row: Int,
    ): List<SplitToken> {
        val tokens = ArrayList<SplitToken>()
        var lastSpaceIndex = -1
        var readingString = false
        string.forEachIndexed { index, char ->
            when (char) {
                ' ' -> {
                    if (!readingString) {
                        if (index != lastSpaceIndex + 1) {
                            val wordStart = lastSpaceIndex + 1
                            tokens.add(
                                createToken(
                                    string.substring(wordStart, index),
                                    row,
                                    wordStart,
                                    index,
                                ),
                            )
                        }
                        lastSpaceIndex = index
                    }
                }
                '"', ':', ';' -> {
                    if (char == '"') {
                        if (readingString) {
                            val wordStart = lastSpaceIndex + 1
                            tokens.add(
                                createToken(
                                    string.substring(wordStart, index + 1),
                                    row,
                                    wordStart,
                                    index,
                                ),
                            )
                            lastSpaceIndex = index
                            readingString = false
                        } else {
                            readingString = true
                        }
                    } else {
                        if (!readingString) {
                            val wordStart = lastSpaceIndex + 1
                            tokens.add(
                                createToken(
                                    string.substring(wordStart, index),
                                    row,
                                    wordStart,
                                    index,
                                ),
                            )
                            tokens.add(
                                createToken(
                                    char.toString(),
                                    row,
                                    index,
                                    index,
                                ),
                            )
                            lastSpaceIndex = index
                        }
                    }
                }
            }
        }
        return tokens
    }

    private fun split(string: String): List<SplitToken> {
        val result = ArrayList<SplitToken>()
        var columnCounter = 0
        var rowCounter = 0
        var wordStartingColumn = 0
        var actualWord = ""
        for (char in string) {
            if (char == '\n') {
                if (actualWord != "") {
                    result.add(
                        createToken(actualWord, rowCounter, wordStartingColumn, columnCounter - 1),
                    )
                }
                rowCounter += 1
                columnCounter = 0
                wordStartingColumn = 0
                actualWord = ""
            } else if (char == ' ') {
                if (actualWord == "") {
                    columnCounter += 1
                    wordStartingColumn += 1
                    continue
                }
                result.add(
                    createToken(actualWord, rowCounter, wordStartingColumn, columnCounter - 1),
                )
                columnCounter += 1
                wordStartingColumn = columnCounter
                actualWord = ""
            } else {
                actualWord += char
                columnCounter += 1
            }
        }
        if (actualWord != "") {
            result.add(
                createToken(actualWord, rowCounter, wordStartingColumn, columnCounter - 1),
            )
        }
        return result
    }

    private fun createToken(
        word: String,
        row: Int,
        firsCol: Int,
        lastCol: Int,
    ): SplitToken {
        val startingPos = Position(row, firsCol)
        val endingPos = Position(row, lastCol)
        return SplitToken(startingPos, endingPos, word)
    }
}
