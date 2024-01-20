package com.example.btdozimetr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView

class Polimaster : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_polimaster)

        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val currentText = findViewById<TextView>(R.id.currentText)
        val gray_button = findViewById<ImageView>(R.id.gray_button)
        val gray_button_2 = findViewById<ImageView>(R.id.gray_button_2)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                currentText.text = String.format("%.2f",13.33 * progress) + " Зв/ч"
                progressBar.progress = 100 - progress
                if(progress < 33){
                    gray_button.visibility = View.VISIBLE
                    gray_button_2.visibility = View.VISIBLE
                }
                if(progress in 33..66){
                    gray_button.visibility = View.VISIBLE
                    gray_button_2.visibility = View.INVISIBLE
                }
                if(progress > 66){
                    gray_button.visibility = View.INVISIBLE
                    gray_button_2.visibility = View.INVISIBLE
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Метод вызывается, когда пользователь начинает перемещать ползунок SeekBar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Метод вызывается, когда пользователь заканчивает перемещать ползунок SeekBar
            }
        })

    }

    fun onClickGoMain(view :View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}