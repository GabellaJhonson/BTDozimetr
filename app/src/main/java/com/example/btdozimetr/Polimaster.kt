package com.example.btdozimetr

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat


var lnumber = 50
var tnumber = 500
var lastNotificationTime = 0L


class Polimaster : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
//    private lateinit var handler: Handler
    private lateinit var progressBar: ProgressBar
    private lateinit var currentText: TextView
    private lateinit var gray_button: ImageView
    private lateinit var gray_button_2: ImageView


@SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_polimaster)

        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        currentText = findViewById<TextView>(R.id.currentText)
        gray_button = findViewById<ImageView>(R.id.gray_button)
        gray_button_2 = findViewById<ImageView>(R.id.gray_button_2)
       

        val lowBorder = findViewById<EditText>(R.id.lowBorder)
        val topBorder = findViewById<EditText>(R.id.topBorder)


        startProgressUpdate()


        lowBorder.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                lnumber = s.toString().toIntOrNull() ?: -1
                if (lnumber == -1) {
                    Toast.makeText(this@Polimaster, "Некорректные данные!", Toast.LENGTH_LONG).show()
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Не требуется
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Не требуется
            }
        })

        topBorder.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tnumber = s.toString().toIntOrNull() ?: -1
                if (tnumber == -1) {
                    Toast.makeText(this@Polimaster, "Некорректные данные!", Toast.LENGTH_LONG).show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed
            }
        })

    }

    private fun startProgressUpdate() {
        handler.postDelayed(object : Runnable {
            override fun run() {

                val currentZivert = TestDataGenerator.nextValue(progressBar.progress.toDouble())

                currentText.text = String.format("%.2f", currentZivert) + " мкР/ч"
                progressBar.progress = (100 - currentZivert/10).toInt()
                if(currentZivert < lnumber){
                    gray_button.visibility = View.VISIBLE
                    gray_button_2.visibility = View.VISIBLE
                }
                if(currentZivert in lnumber.toDouble()..tnumber.toDouble()){
                    gray_button.visibility = View.VISIBLE
                    gray_button_2.visibility = View.INVISIBLE
                    sendNotification_yellow()
                }
                if(currentZivert > tnumber){
                    gray_button.visibility = View.INVISIBLE
                    gray_button_2.visibility = View.INVISIBLE
                    sendNotification_red()
                }
                // Повторное выполнение задачи через 1 секунду
                handler.postDelayed(this, 300)
            }
        }, 300)
    }
    override fun onDestroy() {
        super.onDestroy()
        // Останавливаем обновление, чтобы избежать утечек памяти
        handler.removeCallbacksAndMessages(null)
}
    fun onClickGoMain(view :View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun sendNotification_yellow() {
        val fiveMinutesInMillis = 5 * 60 * 1000
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastNotificationTime > fiveMinutesInMillis) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationId = 1
            val channelId = "channel_id"
            val channelName = "Channel Name"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }

            val notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Use your app icon
                .setContentTitle("Внимание!")
                .setContentText("Уровень радиации выше первой отметки")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            notificationManager.notify(notificationId, notification)
            lastNotificationTime = System.currentTimeMillis()
        }
    }
    fun sendNotification_red() {
        val fiveMinutesInMillis = 5 * 60 * 1000
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastNotificationTime > fiveMinutesInMillis) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationId = 1
            val channelId = "channel_id"
            val channelName = "Channel Name"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }

            val notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Use your app icon
                .setContentTitle("Опасно!")
                .setContentText("Уровень радиации выше второй отметки")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            notificationManager.notify(notificationId, notification)
            lastNotificationTime = System.currentTimeMillis()
        }
    }
}

