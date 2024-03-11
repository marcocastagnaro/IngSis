package org.example.lexer

import org.example.Token.Types

class Mapper (private var map : Map<String, Types>){

    fun getMap () : Map<String, Types>{
        return map
    }
}