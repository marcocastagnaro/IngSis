package org.example.lexer

import org.example.Token.Position

class SplitToken (private val InitialPosition : Position, private val FinalPosition : Position, private val value : String) {

    fun getInitialPosition() : Position{
        return InitialPosition
    }
    fun getValue () : String{
        return value
    }
    fun getFinalPosition() : Position{
        return FinalPosition
    }

}