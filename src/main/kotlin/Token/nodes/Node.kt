package org.example.Token.nodes

import org.example.Token.Token

interface Node {
    fun getValue(): Token

    fun getLeft(): Node? = null

    fun getRight(): Node? = null
}
