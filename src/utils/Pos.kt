package utils

data class Pos(val x: Int, val y: Int, val c: Char) {
    override fun toString(): String {
        return "($x, $y) $c"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return if (other is Pos)
            (this.x == other.x && this.y == other.y)
        else false
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + c.hashCode()
        return result
    }
}