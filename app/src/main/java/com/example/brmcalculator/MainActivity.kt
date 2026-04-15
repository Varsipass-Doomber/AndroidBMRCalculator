package com.example.brmcalculator

import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var maleButton : ImageButton
    lateinit var femaleButton : ImageButton
    lateinit var calculateButton : Button
    lateinit var clearButton : Button
    lateinit var infoButton : Button
    lateinit var heightEditText : EditText
    lateinit var weightEditText : EditText
    lateinit var ageEditText : EditText
    lateinit var bmrMessageTextView : TextView
    lateinit var sitLifestyleTextView : TextView
    lateinit var smallLifestyleTextView : TextView
    lateinit var mediumLifestyleTextView : TextView
    lateinit var strongLifestyleTextView : TextView
    lateinit var maxLifestyleTextView : TextView
    var isGenderChosen : Boolean = false
    var selectedGender : String = "None"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        maleButton = findViewById(R.id.male_button)
        femaleButton = findViewById(R.id.female_button)
        calculateButton = findViewById(R.id.calculate_button)
        clearButton = findViewById(R.id.clear_button)
        infoButton = findViewById(R.id.learn_more_button)

        heightEditText = findViewById(R.id.people_height_editText)
        weightEditText = findViewById(R.id.people_weight_editText)
        ageEditText = findViewById(R.id.people_age_editText)

        bmrMessageTextView = findViewById(R.id.bmr_TextView)
        sitLifestyleTextView = findViewById(R.id.sit_lifestyle_textView)
        smallLifestyleTextView = findViewById(R.id.small_lifestyle_textView)
        mediumLifestyleTextView = findViewById(R.id.medium_lifestyle_textView)
        strongLifestyleTextView = findViewById(R.id.strong_lifestyle_textView)
        maxLifestyleTextView = findViewById(R.id.max_lifestyle_textView)
    }

    fun onGenderButtonClick(v : View) {
        isGenderChosen = true

        when (v.id) {
            R.id.male_button -> {
                selectedGender = "Male"
                setImageButtonColor(maleButton, R.color.selected_gender_button_color)
                setImageButtonColor(femaleButton, R.color.button_color)
            }

            R.id.female_button -> {
                selectedGender = "Female"
                setImageButtonColor(femaleButton, R.color.selected_gender_button_color)
                setImageButtonColor(maleButton, R.color.button_color)
            }
            else -> {
                selectedGender = "None"
                setImageButtonColor(maleButton, R.color.button_color)
                setImageButtonColor(femaleButton, R.color.button_color)
            }
        }
    }

    fun onCalculateButtonClick(v : View) {
        calculateBMR()
    }

    fun onClearButtonClick(v : View) {
        heightEditText.text = null
        weightEditText.text = null
        ageEditText.text = null
        bmrMessageTextView.text = "Ваш BMR"

        sitLifestyleTextView.text = "Сидячий: "
        smallLifestyleTextView.text = "Малая активность: "
        mediumLifestyleTextView.text = "Средняя активность: "
        strongLifestyleTextView.text = "Сильная активность: "
        maxLifestyleTextView.text = "Максимальная активность: "

        selectedGender = "None"
        setImageButtonColor(maleButton, R.color.button_color)
        setImageButtonColor(femaleButton, R.color.button_color)
    }

    fun onLearnMoreButtonClick(v : View) {
        val intent = Intent(this, InformationActivity::class.java)
        startActivity(intent)
    }

    fun calculateBMR() {
        if (heightEditText.text.toString().isNullOrBlank() || weightEditText.text.toString().isNullOrBlank() ||
            ageEditText.text.toString().isNullOrBlank()) {
            AlertDialog.Builder(this)
                .setTitle("Ошибка ввода данных")
                .setMessage("Все поля должны быть заполнены")
                .setPositiveButton("OK") { _, _ -> }
                .setNegativeButton("NOT OK") { _, _ -> }
                .show()
            return
        }

        val weight : Double = weightEditText.text.toString().toDouble()
        val height : Double = heightEditText.text.toString().toDouble()
        val age : Double = ageEditText.text.toString().toDouble()

        if (weight > 700 || weight <= 0
                || height > 300 || height <= 0
                || age > 150 || age <= 0) {
            AlertDialog.Builder(this)
                .setTitle("Ошибка ввода данных")
                .setMessage("Нереалистичные данные")
                .setPositiveButton("OK") { _, _ -> }
                .show()
            return
        }

        var bmr : Double = when(selectedGender) {
            "Male" -> {
                66.0 + weight * 13.7 +
                        height * 5 -
                        age * 6.8
            }

            "Female" -> {
                655.0 + weight * 9.6 +
                        height * 1.8 -
                        age * 4.7
            }
            else -> {
                AlertDialog.Builder(this)
                    .setTitle("Выберите пол")
                    .setPositiveButton("OK") { _, _ -> }
                    .show()
                return
            }
        }

        if (bmr <= 0) {
            val toast = Toast.makeText(applicationContext, "Нереалистичные данные", Toast.LENGTH_SHORT)
            toast.show()
            return
        }

        bmr = Math.round(bmr * 100.0) / 100.0
        bmrMessageTextView.text = "Ваш BMR $bmr"

        sitLifestyleTextView.text = "Сидячий: " + Math.round((bmr * 1.2) * 100.0) / 100.0
        smallLifestyleTextView.text = "Малая активность: " + Math.round((bmr * 1.375) * 100.0) / 100.0
        mediumLifestyleTextView.text = "Средняя активность: " + Math.round((bmr * 1.55) * 100.0) / 100.0
        strongLifestyleTextView.text = "Сильная активность: " + Math.round((bmr * 1.725) * 100.0) / 100.0
        maxLifestyleTextView.text = "Максимальная активность: " + Math.round((bmr * 1.9) * 100.0) / 100.0

    }

    fun setImageButtonColor(button : ImageButton, @ColorRes colorRes: Int) {
        val colorInt = ContextCompat.getColor(button.context, colorRes)
        button.backgroundTintList = ColorStateList.valueOf(colorInt)
    }


}