package org.example.Token

class Token (private var type: Types, private var value : String, private var initialPosition : Position, private var finalPosition : Position){

    fun getType() : Types{
        return type
    }
    fun getValue () : String{
        return value
    }
    fun getInitialPosition() : Position{
        return initialPosition
    }
    fun getFinalPosition() : Position{
        return finalPosition
    }
}