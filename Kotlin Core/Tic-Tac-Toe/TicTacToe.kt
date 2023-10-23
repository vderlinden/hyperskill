package tictactoe

import kotlin.math.abs
import kotlin.system.exitProcess

class TicTacToe {
    private var gameBoard: MutableList<MutableList<Char>> = mutableListOf(
        MutableList(3) { '_' },
        MutableList(3) { '_' },
        MutableList(3) { '_' }
    )
    private var player: Char = 'X'

    init {
        this.startGame()
    }

    private fun startGame() {
        this.renderGame()
        this.checkGame(this.gameBoard)
        this.getMove()
    }

    private fun getMove(): List<Int> {
        while (true) {
            println("Player ${this.player} enter the cell coordinates:")
            val userInput = readln()

            if (validateDigits(userInput)) {
                println("You should enter numbers!")
                continue
            }

            val numbers = getNumbers(userInput)
            val (axisY, axisX) = numbers

            if (!validateRange(axisX, axisY)) {
                println("Coordinates should be from 1 to 3!")
                continue
            }

            if (validateTaken(axisY, axisX, gameBoard)) {
                println("This cell is occupied! Choose another one!")
            } else {
                setMove(numbers)
            }
        }
    }

    private fun validateDigits(input: String): Boolean {
        val string = input.replace(" ", "")
        return string.any { !it.isDigit() }
    }

    private fun getNumbers(input: String): List<Int> {
        val string = input.replace(" ", "")
        return string.map { it - '0' }
    }

    private fun validateRange(x: Int, y: Int): Boolean {
        return x in 1..3 && y in 1..3
    }

    private fun validateTaken(axisY: Int, axisX: Int, gameBoard: MutableList<MutableList<Char>>): Boolean {
        return gameBoard[axisY - 1][axisX - 1] != '_'
    }

    private fun setMove(numbers: List<Int>) {
        val (axisY, axisX) = numbers
        gameBoard[axisY - 1][axisX - 1] = this.player
        this.setPlayer()
        this.startGame()
    }

    private fun setPlayer() {
        if (this.player == 'X') {
            this.player = 'O'
        } else {
            this.player = 'X'
        }
    }

    private fun checkGame(gameState: MutableList<MutableList<Char>>) {
        val winningLists: MutableList<MutableList<Char>> = mutableListOf(
            mutableListOf(gameState[0][0], gameState[0][1], gameState[0][2]), // First row
            mutableListOf(gameState[1][0], gameState[1][1], gameState[1][2]), // Second row
            mutableListOf(gameState[2][0], gameState[2][1], gameState[2][2]), // Third row
            mutableListOf(gameState[0][0], gameState[1][0], gameState[2][0]), // First column
            mutableListOf(gameState[0][1], gameState[1][1], gameState[2][1]), // Second column
            mutableListOf(gameState[0][2], gameState[1][2], gameState[2][2]), // Third column
            mutableListOf(gameState[0][0], gameState[1][1], gameState[2][2]), // Diagonal left to right
            mutableListOf(gameState[0][2], gameState[1][1], gameState[2][0]) // Diagonal right to left
        )

        val stringFromGame = gameState.toString()
        val emptyCells = stringFromGame.count { it == '_' }
        val countX: Int = stringFromGame.count { it == 'X' }
        val countY: Int = stringFromGame.count { it == 'O' }
        val diff = abs(countX - countY)

        val winnerX = this.winner('X', winningLists)
        val winnerO = this.winner('O', winningLists)

        val result = when {
            diff >= 2 -> "Impossible" // One player has too many actions
            winnerX && winnerO -> "Impossible" // Both players have won
            winnerX -> "X wins" // Player X wins
            winnerO -> "O wins" // Player O wins
            !winnerX && !winnerO && emptyCells == 0 -> "Draw" // It's a draw
            else -> "Game not finished"
        }

        if (result == "Game not finished") {
            return
        } else {
            println(result)
            exitProcess(0)
        }
    }

    private fun winner(player: Char, winningLists: MutableList<MutableList<Char>>): Boolean {
        var playerWon = false

        for (list in winningLists) {
            val count: Int = list.count { it == player }
            if (count == 3) playerWon = true
        }

        return playerWon
    }

    // Render game on screen
    private fun renderGame() {

        println("---------")

        for (row in gameBoard) {
            print("| ")
            for (char in row) {
                print(char)
                print(" ")
            }
            println("|")
        }
        println("---------")
    }
}

// Create 3 rows from string for rendering
/*
fun gameLogic(input: String): MutableList<MutableList<Char>> {
    return mutableListOf(
        mutableListOf(input[0], input[1], input[2]),
        mutableListOf(input[3], input[4], input[5]),
        mutableListOf(input[6], input[7], input[8])
    )
}
*/