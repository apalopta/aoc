package utils

fun List<String>.toPosArray(): Array<Array<Pos>> =
    Array(size) { x ->
        Array(this[x].length) { y ->
            Pos(x, y, this[x][y])
        }
    }

fun Array<Array<Pos>>.draw() =
    this.joinToString(separator = "\n") { row ->
        row.joinToString("") { it.c.toString() }
    }

fun Array<Pos>.draw() = joinToString("") { "${it.c}"}
