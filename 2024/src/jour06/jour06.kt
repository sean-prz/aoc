package jour06

import helper.Helper

fun main() {
    Helper.startTimer()
    val input = Helper.readInput("06")
    Part1And2.main(input)
    Helper.stopTimer()
}
object Part1And2 {
    // Constants
    const val OBSTACLE = '#'
    const val GUARD = '^'
    const val EMPTY = '.'


    // Enums
    enum class Direction(val dx: Int, val dy: Int) {
        UP(-1, 0),
        RIGHT(0, 1),
        DOWN(1, 0),
        LEFT(0, -1);

        fun turnRight() : Direction {
            return entries[(ordinal + 1) % entries.size]
        }
    }

    // Data classes
    data class Position(val pos: Pair<Int,Int>, val dir: Direction) {
        operator fun plus (dir: Direction) = Position(pos.first + dir.dx to pos.second + dir.dy, dir)
        fun turnRight(): Position {return Position(pos, dir.turnRight())}
    }

    // Functions
    fun makeGrid(input:String): List<List<Char>> {
        return input.lines().map { it.toList() }.filterNot { it.isEmpty() }
    }
    fun findGuard(grid: List<List<Char>>): Pair<Int,Int> {
        val x = grid.indexOfFirst { it.contains(GUARD) }
        val y = grid[x].indexOf(GUARD)
        return x to y
    }
    private fun move(pos: Position, grid: List<List<Char>>) : Position {
        val newPos = pos + pos.dir

            if (grid[newPos.pos.first][newPos.pos.second] == OBSTACLE)
                return pos.turnRight()
            else
                return newPos
    }
    fun detectLoop(grid: List<List<Char>>, startPos: Position) : Boolean {
        val visited = mutableSetOf<Position>()
        var pos = startPos
        try {
            while (true) {
                pos = move(pos, grid)
                // Looping detected if we are on a cell we have already visited with the same direction
                if(!visited.add(pos)) {
                    return true
                }
            }
        } catch (e : Exception) {
            return false
        }
    }

    fun main(input: String) {
        val defaultGrid = makeGrid(input)
        val startPos = Position(findGuard(defaultGrid), Direction.UP)
        val visited : MutableSet<Position> = mutableSetOf(startPos)

        // Part 1
        try {
            var pos = startPos
            while (true) {
                pos = move(pos, defaultGrid)
                visited.add(pos)
            }
        } catch (_: Exception) { }
        // only keep the cells and not the direction, remove all duplicates cells
        val visitedFiltered = visited.map{it.pos}.toSet()

        //  Part 2
        var loopCounter = 0
        val grid = defaultGrid.map { it.toMutableList() }.toMutableList()
        visitedFiltered.forEach {  (x,y) ->
                if(defaultGrid[x][y] == EMPTY) {
                    grid[x][y] = OBSTACLE
                    loopCounter += if(detectLoop(grid, startPos)) 1 else 0
                    grid[x][y] = EMPTY
                }
        }
        println("Part 1 : ${visitedFiltered.size}")
        println("Part 2 : Found $loopCounter loops")
    }
}


