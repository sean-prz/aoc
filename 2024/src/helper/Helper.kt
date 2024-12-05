package helper

import java.io.File

object Helper {
    var time = System.currentTimeMillis()
    fun readInput(jour: String) : String {
        val file = File( "./src/jour$jour/input$jour.txt")
        return file.readText()
    }
    fun startTimer() {
        time = System.currentTimeMillis()
    }
    fun stopTimer() {
        println("Execution time: ${System.currentTimeMillis() - time} ms")
    }
}