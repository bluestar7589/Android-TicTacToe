package com.example.tictactoe

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Button>
    private lateinit var turnLabel: TextView
    private var isPlayerOneTurn = true
    private var isGameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.logo = getDrawable(R.mipmap.ic_launcher)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // get the array of buttons
        buttons = arrayOf(
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9)
        )

        // get the turn label
        turnLabel = findViewById(R.id.turnLabel)

        // set the event listener for each button when clicked
        for (button in buttons) {
            button.setOnClickListener {
                onButtonClick(button)
            }
        }

        // set the event listener for the new game button
        findViewById<Button>(R.id.newGameButton).setOnClickListener {
            resetGame()
        }

        // keep the state when rotating the screen
        if (savedInstanceState != null) {
            restoreState(savedInstanceState)
        }
    }

    // Handle button clicks from button 1 - 9
    private fun onButtonClick(button: Button) {
        if (isGameOver) return

        if (button.text == getString(R.string.space)) {
            button.text = if (isPlayerOneTurn) "X" else "O"
            if (checkForWinner()) {
                turnLabel.text = if (isPlayerOneTurn) "Player X wins!" else "Player O wins!"
                isGameOver = true
            } else if (isBoardFull()) {
                turnLabel.text = "No more space, click new game to play again!"
                isGameOver = true
            } else {
                isPlayerOneTurn = !isPlayerOneTurn
                turnLabel.text = if (isPlayerOneTurn) "Player X's turn" else "Player O's turn"
            }
        }
    }

    private fun isBoardFull(): Boolean {
        for (button in buttons) {
            if (button.text == getString(R.string.space)) {
                return false
            }
        }
        return true
    }

    // Check for a winner
    private fun checkForWinner(): Boolean {
        val board = Array(3) { row ->
            Array(3) { col ->
                buttons[row * 3 + col].text.toString()
            }
        }

        // Check rows and columns
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != getString(R.string.space)) {
                return true
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != getString(R.string.space)) {
                return true
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != getString(R.string.space)) {
            return true
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != getString(R.string.space)) {
            return true
        }

        return false
    }

    // Reset the game state
    private fun resetGame() {
        for (button in buttons) {
            button.text = getString(R.string.space)
        }
        isPlayerOneTurn = true
        isGameOver = false
        turnLabel.text = "Player X's turn"
    }

    // Save the state when rotating the screen
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isPlayerOneTurn", isPlayerOneTurn)
        outState.putBoolean("isGameOver", isGameOver)
        outState.putStringArray("buttonTexts", buttons.map { it.text.toString() }.toTypedArray())
        outState.putString("turnLabel", turnLabel.text.toString())
    }

    // Restore the state when rotating the screen
    private fun restoreState(savedInstanceState: Bundle) {
        isPlayerOneTurn = savedInstanceState.getBoolean("isPlayerOneTurn")
        isGameOver = savedInstanceState.getBoolean("isGameOver")
        val buttonTexts = savedInstanceState.getStringArray("buttonTexts")
        if (buttonTexts != null) {
            for (i in buttons.indices) {
                buttons[i].text = buttonTexts[i]
            }
        }
        turnLabel.text = savedInstanceState.getString("turnLabel")
    }
}