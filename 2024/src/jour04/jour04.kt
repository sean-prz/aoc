package jour04

import helper.Helper

fun main() {
    val input = Helper.readInput("04")
    println(Part1.main(input))
    println(Part2.main(input))
}

object Part1 {
    fun main(input:String) {
        val grid = convertInputInto2DList(input)
        val allX = findAllXInGrid((grid))
        allX.sumOf {
            listOf(
                isRowRightXMAS(grid, it),
                isRowLeftXMAS(grid, it),
                isColumnDownXMAS(grid, it),
                isColumnUpXMAS(grid, it),
                isDiagonalDownRightXMAS(grid, it),
                isDiagonalDownLeftXMAS(grid, it),
                isDiagonalUpRightXMAS(grid, it),
                isDiagonalUpLeftXMAS(grid, it)
            ).filter { it }.size
        }.let { println(it) }
    }

    // First index is the row, second index is the column
    fun convertInputInto2DList(input: String): List<List<Char>> {
        return input.split("\n").filterNot { it.isEmpty() }.map { it.toList() }
    }

    fun findAllXInGrid(grid: List<List<Char>>): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == 'X') {
                    result.add(Pair(i, j))
                }
            }
        }
        return result
    }

    fun isRowRightXMAS(grid: List<List<Char>>, xCoord: Pair<Int, Int>): Boolean {
        if (xCoord.second + 3 >= grid[0].size) return false
        val (x, y) = xCoord
        return grid[x][y + 1] == 'M' && grid[x][y + 2] == 'A' && grid[x][y + 3] == 'S'
    }
    fun isRowLeftXMAS(grid: List<List<Char>>, xCoord: Pair<Int, Int>): Boolean {
        if (xCoord.second - 3 < 0) return false
        val (x, y) = xCoord
        return grid[x][y - 1] == 'M' && grid[x][y - 2] == 'A' && grid[x][y - 3] == 'S'
    }
    fun isColumnDownXMAS(grid: List<List<Char>>, xCoord: Pair<Int, Int>): Boolean {
        if (xCoord.first + 3 >= grid.size) return false
        val (x, y) = xCoord
        return grid[x + 1][y] == 'M' && grid[x + 2][y] == 'A' && grid[x + 3][y] == 'S'
    }
    fun isColumnUpXMAS(grid: List<List<Char>>, xCoord: Pair<Int, Int>): Boolean {
        if (xCoord.first - 3 < 0) return false
        val (x, y) = xCoord
        return grid[x - 1][y] == 'M' && grid[x - 2][y] == 'A' && grid[x - 3][y] == 'S'
    }
    fun isDiagonalDownRightXMAS(grid: List<List<Char>>, xCoord: Pair<Int, Int>): Boolean {
        if (xCoord.first + 3 >= grid.size || xCoord.second + 3 >= grid[0].size) return false
        val (x, y) = xCoord
        return grid[x + 1][y + 1] == 'M' && grid[x + 2][y + 2] == 'A' && grid[x + 3][y + 3] == 'S'
    }
    fun isDiagonalDownLeftXMAS(grid: List<List<Char>>, xCoord: Pair<Int, Int>): Boolean {
        if (xCoord.first + 3 >= grid.size || xCoord.second - 3 < 0) return false
        val (x, y) = xCoord
        return grid[x + 1][y - 1] == 'M' && grid[x + 2][y - 2] == 'A' && grid[x + 3][y - 3] == 'S'
    }
    fun isDiagonalUpRightXMAS(grid: List<List<Char>>, xCoord: Pair<Int, Int>): Boolean {
        if (xCoord.first - 3 < 0 || xCoord.second + 3 >= grid[0].size) return false
        val (x, y) = xCoord
        return grid[x - 1][y + 1] == 'M' && grid[x - 2][y + 2] == 'A' && grid[x - 3][y + 3] == 'S'
    }
    fun isDiagonalUpLeftXMAS(grid: List<List<Char>>, xCoord: Pair<Int, Int>): Boolean {
        if (xCoord.first - 3 < 0 || xCoord.second - 3 < 0) return false
        val (x, y) = xCoord
        return grid[x - 1][y - 1] == 'M' && grid[x - 2][y - 2] == 'A' && grid[x - 3][y - 3] == 'S'
    }
}
object Part2 {
    fun main(input : String): Int{
        val grid = Part1.convertInputInto2DList(input) // Returns a 2D List of Char
        val allA = findAllAInGrid(grid) // Returns a list of coords of all A in the grid
        return allA.filter { hasValidCross(grid, it) }.size
    }

    fun findAllAInGrid(grid: List<List<Char>>): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == 'A') {
                    result.add(Pair(i, j))
                }
            }
        }
        return result
    }

    /**
     * Check if the characters sourrunding an A form a valid X-MAS cross
     * @param grid: 2D List of Char, representing the input
     * @param aCoord: A pair of Int, representing the coordinates of one A in the grid
     */
    fun hasValidCross(grid: List<List<Char>>, aCoord: Pair<Int, Int>) : Boolean {
        val (x, y) = aCoord
        if (x < 1 || y < 1 || x + 1 >= grid.size  || y + 1 >= grid[0].size) return false
        val topLeft: Char = grid[x - 1][y - 1]
        val topRight: Char = grid[x - 1][y + 1]
        val bottomLeft: Char = grid[x + 1][y - 1]
        val bottomRight: Char = grid[x + 1][y + 1]
        val crossString = "$topLeft$topRight$bottomLeft$bottomRight"
        val listOfValidCross = listOf("MSMS", "MMSS", "SSMM", "SMSM")
        return listOfValidCross.contains(crossString)
    }
}