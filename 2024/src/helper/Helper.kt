package helper

import java.io.File

object Helper {
    fun readInput(jour: String) : String {
        val file = File( "./src/jour$jour/input$jour.txt")
        return file.readText()
    }
}