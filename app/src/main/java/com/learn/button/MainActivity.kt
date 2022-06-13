package com.learn.button

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    companion object {
        var myIntent : Intent? = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NotificationServices().stopSelf()

        myIntent = Intent(this@MainActivity, user::class.java)
        NotificationCounter()

        var timer = object :CountDownTimer(2500,500){

            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                finish()
            }
        }
        timer.start()
    }

    // notifications
    val notificationChannelId = "1234"

    @RequiresApi(Build.VERSION_CODES.M)
    fun showNotification(title: String, text: String) {

        val myNotificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this@MainActivity,String())

        val myIntentNotification = Intent(this@MainActivity, user::class.java)
        myIntentNotification.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val resultPendingIntent = PendingIntent.getActivities(
            this@MainActivity, 25,
            arrayOf(myIntentNotification),
            PendingIntent.FLAG_IMMUTABLE
        )

        mBuilder.setColor(304000)
        mBuilder.setSmallIcon(R.drawable.ic_baseline_sentiment_very_satisfied_24)
        mBuilder.setContentTitle(title)
        mBuilder.setContentText(text)
        mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
        mBuilder.setContentIntent(resultPendingIntent)
        mBuilder.setAutoCancel(false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notiChannel = NotificationChannel(
                notificationChannelId,
                "news",
                NotificationManager.IMPORTANCE_LOW
            )
            mBuilder.setChannelId(notificationChannelId)
            myNotificationManager.createNotificationChannel(notiChannel)
        }
        myNotificationManager.notify(1, mBuilder.build())

    }

    fun NotificationCounter(){

        val countDown = object : CountDownTimer(2000, 500) {
            override fun onTick(millisUntilFinished: Long) {
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onFinish() {
                showNotification(
                    "سبح اللَّه",
                    "لا إِلَهَ إِلا أَنتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَِ"
                )

                Toast.makeText(
                    this@MainActivity,
                    "لا إِلَهَ إِلا أَنْتَ سُبْحَانَكَ إِنِّي كُنْتُ مِنَ الظَّالِمِينَ",
                    Toast.LENGTH_LONG
                ).show()

                startActivity(myIntent)
            }
        }
        countDown.start()
    }

}
