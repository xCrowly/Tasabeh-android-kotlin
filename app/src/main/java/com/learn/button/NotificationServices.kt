package com.learn.button

import android.app.*
import android.app.Notification.FOREGROUND_SERVICE_DEFAULT
import android.app.Notification.FOREGROUND_SERVICE_IMMEDIATE
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.lang.Exception
import kotlin.random.Random

class NotificationServices : Service() {

    override fun onBind(p0: Intent?): IBinder? = null

    companion object {
        var nameArray = arrayListOf(
            "سُبْحَانَ اللَّهِ",
            "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
            "سُبْحَانَ اللَّهِ وَالْحَمْدُ لِلَّهِ",
            "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ ، سُبْحَانَ اللَّهِ الْعَظِيمِ",
            "الْلَّهُ أَكْبَرُ",
            "لَا إِلَهَ إِلَّا اللَّهُ",
            "أستغفر اللَّه",
            "لا إِلَهَ إِلا أَنتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَ",
            "الْحَمْدُ للّهِ رَبِّ الْعَالَمِينَ",
            "لا حَوْلَ وَلا قُوَّةَ إِلا باللهَّ",
            "الْحَمْدُ لِلَّهِ حَمْدًا كَثِيرًا طَيِّبًا مُبَارَكًا فِيهِ",
            "الْلَّهُم صَلِّ وَسَلِم وَبَارِك عَلَى سَيِّدِنَا مُحَمَّد",
            "سُبْحَانَ الْلَّهِ، وَالْحَمْدُ لِلَّهِ، وَلَا إِلَهَ إِلَّا الْلَّهُ، وَالْلَّهُ أَكْبَرُ",
            "اللَّهُ أَكْبَرُ كَبِيرًا ، وَالْحَمْدُ لِلَّهِ كَثِيرًا ، وَسُبْحَانَ اللَّهِ بُكْرَةً وَأَصِيلاً",
            "لَا إلَه إلّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلُّ شَيْءِ قَدِيرِ",
        )

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        countDownNotification()
        return START_STICKY
    }

    val notifcationChannelId = "1236"

    @RequiresApi(Build.VERSION_CODES.M)
    fun showNotification(title: String, text: String) {

        val myNotificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this@NotificationServices, String())
                //.setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE)

        val myIntentNotification = Intent(this@NotificationServices, user::class.java)
        myIntentNotification.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val resultPendingIntent = PendingIntent.getActivities(
            this@NotificationServices, 25,
            arrayOf(myIntentNotification),
            PendingIntent.FLAG_IMMUTABLE
        )

        mBuilder.foregroundServiceBehavior = FOREGROUND_SERVICE_IMMEDIATE
        mBuilder.setColor(304000)
        mBuilder.setSmallIcon(R.drawable.ic_baseline_sentiment_very_satisfied_24)
        mBuilder.setContentTitle(title)
        mBuilder.setContentText(text)
        mBuilder.setContentIntent(resultPendingIntent)
        mBuilder.setAutoCancel(false)
        mBuilder.setOngoing(true)
        mBuilder.setSilent(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notiChanal = NotificationChannel(
                notifcationChannelId,
                "Tasabeh",
                NotificationManager.IMPORTANCE_MIN
            )
            mBuilder.setChannelId(notifcationChannelId)
            myNotificationManager.createNotificationChannel(notiChanal)
            mBuilder.setSmallIcon(R.drawable.ic_baseline_sentiment_very_satisfied_24)

        }
        myNotificationManager.notify(2, mBuilder.build())

    }


    override fun onDestroy() {

        Intent(this@NotificationServices, NotificationServices::class.java).also {
            super.startService(it)
        }

    }

     fun countDownNotification() {

        var countDownTimer = object : CountDownTimer(4000, 500) {

            override fun onTick(millisUntilFinished: Long) {
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onFinish() {
                showNotification("سبح الله", randomAzkar())
                countDownNotification()
            }
        }
        countDownTimer.start()

    }

    fun randomAzkar(): String {

        val randomId = List(1) { Random.nextInt(0, 14) }
        val azkar = nameArray.elementAt(randomId[0])
        return azkar
    }

}






