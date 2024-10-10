package com.mobile_systems.fourth_lab_4_1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.app.ActivityCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private val questions = listOf(
        Question("Канберра это столица Австралии?", true),
        Question("Сейчас зима?", false),
        Question("Мокрая ли вода?", true)
    )

    private var currentIndex = 0
    private var correctAnswersCount = 0

    private lateinit var questionTextView: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionTextView = findViewById(R.id.question_text_view)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        backButton = findViewById(R.id.back_button)

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("currentIndex", 0)
            correctAnswersCount = savedInstanceState.getInt("correctAnswersCount", 0)
            updateQuestion()
        }

        trueButton.setOnClickListener { checkAnswer(true) }
        falseButton.setOnClickListener { checkAnswer(false) }
        nextButton.setOnClickListener { moveToNext() }
        backButton.setOnClickListener { moveToPrevious() }
        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentIndex", currentIndex)
        outState.putInt("correctAnswersCount", correctAnswersCount)
    }

    private fun updateQuestion() {
        val questionText = questions[currentIndex].text
        questionTextView.text = questionText
        trueButton.visibility = View.VISIBLE
        falseButton.visibility = View.VISIBLE
        nextButton.visibility = View.VISIBLE

        if (currentIndex == questions.size - 1) {
            nextButton.isEnabled = false
            nextButton.visibility = View.INVISIBLE
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questions[currentIndex].answer
        if (userAnswer == correctAnswer) {
            correctAnswersCount++
            Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Неверно!", Toast.LENGTH_SHORT).show()
        }
        trueButton.visibility = View.INVISIBLE
        falseButton.visibility = View.INVISIBLE

        if (currentIndex == questions.size - 1) {
            showResult()
        }
    }

    private fun moveToNext() {
        currentIndex = (currentIndex + 1) % questions.size
        updateQuestion()
    }

    private fun moveToPrevious() {
        if (currentIndex > 0) {
            currentIndex--
            updateQuestion()
        }
    }

    private fun showResult() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Конец")
            .setMessage("У вас $correctAnswersCount ответа из ${questions.size} правильные!")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}

data class Question(val text: String, val answer: Boolean)