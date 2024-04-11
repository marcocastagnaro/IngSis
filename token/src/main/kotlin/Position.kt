package org.example

class Position(private var row: Int, private var column: Int) {
    fun getRow(): Int {
        return row
    }

    fun getColumn(): Int {
        return column
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Position) return false

        return row == other.row && column == other.column
    }
}
