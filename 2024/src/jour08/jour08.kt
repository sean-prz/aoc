package jour08

import helper.Helper

val testInput =
    """
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............
    """.trimIndent()

fun main() {
    val input = Helper.readInput("08")
    Part1.main(input)
    Part2.main(input)
}

object Part1 {
    fun main(input: String) {
        val grid = Shared.makeGrid(input)
        val frequencies = Shared.findUniqueChars(input)
        val antiNodes: MutableSet<Pair<Int, Int>> = mutableSetOf()
        frequencies.forEach {
            val antennas = Shared.findAllAntennasOfAFrequency(grid, it)
            antennas.forEach { antenna ->
                antennas.forEach { otherAntenna ->
                    if (antenna != otherAntenna) {
                        val dx = otherAntenna.first - antenna.first
                        val dy = otherAntenna.second - antenna.second
                        val antiNode = antenna.first - dx to antenna.second - dy
                        antiNodes.add(antiNode)
                    }
                }
            }
        }
        println(antiNodes.filter { Shared.isAntiNodeValid(it, grid) }.size)
    }
}

object Part2 {
    fun main(input: String) {
        val grid = Shared.makeGrid(input)
        val frequencies = Shared.findUniqueChars(input)
        val antiNodes: MutableSet<Pair<Int, Int>> = mutableSetOf()
        frequencies.forEach {
            val antennas = Shared.findAllAntennasOfAFrequency(grid, it)
            antennas.forEach { antenna ->
                antennas.forEach { otherAntenna ->
                    if (antenna == otherAntenna) { return@forEach }
                    antiNodes.add(otherAntenna)
                    val dx = otherAntenna.first - antenna.first
                    val dy = otherAntenna.second - antenna.second
                    var antiNode = antenna.first - dx to antenna.second - dy
                    while (Shared.isAntiNodeValid(antiNode, grid)) {
                        antiNodes.add(antiNode)
                        antiNode = antiNode.first - dx to antiNode.second - dy
                    }

                }
            }
        }
        println(antiNodes.size)
    }
}

object Shared {
    fun makeGrid(input: String): List<List<Char>> {
        return input.lines().map { it.toList() }.filterNot { it.isEmpty() }
    }
    fun findUniqueChars(input : String): Set<Char> {
        return input.filter { it.isLetterOrDigit() }.toSet()
    }
    fun findAllAntennasOfAFrequency(grid: List<List<Char>>, frequency: Char): List<Pair<Int, Int>> {
        return grid.flatMapIndexed { i, row ->
            row.mapIndexedNotNull { j, cell ->
                if (cell == frequency) i to j else null
            }
        }
    }
    fun isAntiNodeValid(antiNode: Pair<Int, Int>, grid: List<List<Char>>): Boolean {
        return antiNode.first >= 0 && antiNode.second >= 0 && antiNode.first < grid.size && antiNode.second < grid[0].size
    }
}