package com.example.brmcalculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class MarathonSkillsInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_marathon_skills_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val infoTextView : TextView = findViewById(R.id.info_textView)
        lateinit  var text : String

        try {
            text = resources.openRawResource(R.raw.marathon_skills_info)
                .bufferedReader().readText()
            Log.i("MarathonSkillsInfo", "Successfully loaded information from file")
        }
        catch (ex: IOException) {
            text = "Ошибка чтения: ${ex.message}"
            Log.e("MarathonSkillsInfo", "Cannot load information from file")
        }

        infoTextView.text = text
    }

    fun onReturnBackButtonClick(v : View) {
        finish()
    }
}