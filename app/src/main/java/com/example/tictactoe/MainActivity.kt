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
    }

    // Handle button clicks from button 1 - 9
    private fun onButtonClick(button: Button) {
        if (button.text == getString(R.string.space)) {
            button.text = if (isPlayerOneTurn) "X" else "O"
            isPlayerOneTurn = !isPlayerOneTurn
            turnLabel.text = if (isPlayerOneTurn) "Player X's turn" else "Player O's turn"
        }
    }

    // Reset the game state
    private fun resetGame() {
        for (button in buttons) {
            button.text = getString(R.string.space)
        }
        turnLabel.text = "Player X's turn"
    }
}