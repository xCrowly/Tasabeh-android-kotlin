package com.learn.button

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaPlayer;
import android.os.Build
import android.os.CountDownTimer
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_user.*

class user : AppCompatActivity() {

    companion object {
        var notificationIntent: Intent? = null
    }

    //array list variables
    var button2Txt: SharedPreferences? = null
    var buttonTxtEditor: SharedPreferences.Editor? = null

    //switches variables
    var switch1Checked: SharedPreferences? = null
    var switch1CheckedEditor: SharedPreferences.Editor? = null
    var switch2Checked: SharedPreferences? = null
    var switch2CheckedEditor: SharedPreferences.Editor? = null
    var switch3Checked: SharedPreferences? = null
    var switch3CheckedEditor: SharedPreferences.Editor? = null
    var database: HistoryDataBase? = null

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        var count = 0
        var countingNumber = "33"
        var countingValue = "33"
        var countingCurrentValue = 33
        var countingTotalValue = 33
        val textCount = textView6.text


        //Notification service initiator
        notificationIntent = Intent(this@user, NotificationServices::class.java)

        try {
            NotificationServices().stopService(notificationIntent)
        } catch (e: java.lang.Exception) {
            startService(notificationIntent)
        }

        //array list sittings
        button2Txt = getSharedPreferences("text2", Context.MODE_PRIVATE)
        buttonTxtEditor = button2Txt!!.edit()
        button2.text = button2Txt!!.getString("text2", "سبح الله").toString()

        //switches
        switch1Checked = getSharedPreferences("Checked1", Context.MODE_PRIVATE)
        switch1CheckedEditor = switch1Checked!!.edit()

        switch2Checked = getSharedPreferences("Checked2", Context.MODE_PRIVATE)
        switch2CheckedEditor = switch2Checked!!.edit()

        switch3Checked = getSharedPreferences("Checked3", Context.MODE_PRIVATE)
        switch3CheckedEditor = switch3Checked!!.edit()

        //database
        database = HistoryDataBase(this, "history.db", null, 1)

        //array list Intent
        editTextAzkar.setOnClickListener() {
            val openMainActivity2 = Intent(this@user, azkar::class.java)
            openMainActivity2.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivityIfNeeded(openMainActivity2, 0)
        }

        //counter settings
        buttonCounter.setOnClickListener() {
            var checkCountInput = editTextCounter!!.text.toString()
            try {
                if (checkCountInput.toInt() > 0 || checkCountInput != null) {
                    countingNumber = editTextCounter.text.toString()
                    textCounter.setText(textView6.text.toString() + countingNumber.toInt())
                    countingValue = editTextCounter.text.toString()
                    countingCurrentValue = countingValue.toInt()
                    countingTotalValue = countingValue.toInt()
                }
            } catch (ex: Exception) {
                countingNumber = "0"
                countingValue = "-1"
                countingCurrentValue = -1
                countingTotalValue = -1
                textCounter.setText(textView6.text.toString() + countingNumber.toInt())
            }
        }

        //switches functions >>
        button2.setOnClickListener() {
            button2.isHapticFeedbackEnabled = switch1Checked!!.getBoolean("Checked1", true)

            if (switch2Checked!!.getBoolean("Checked2", true)) {
                var mp: MediaPlayer = MediaPlayer.create(this, R.raw.button_click)
                mp.start()
                mp.setOnCompletionListener { mp.release() }
            }

            if (switch3Checked!!.getBoolean("Checked3", true)) {
                if (count == (countingTotalValue - 1)) {
                    var mp2: MediaPlayer = MediaPlayer.create(this, R.raw.sobhan_allah_button)
                    mp2.start()
                    mp2.setOnCompletionListener { mp2.release() }
                    countingTotalValue += countingCurrentValue

                    var mp: MediaPlayer = MediaPlayer.create(this, R.raw.button_click)
                    mp.start()
                    mp.setOnCompletionListener { mp.release() }
                }
            }

            count += 1
            if (countingNumber.toInt() < count) {
                countingNumber += 1
            }

            textView2.text = count.toString()
            count = count

            var name = button2.text.toString()
            var Count = database!!.getId(name)

            if (Count.moveToFirst()) {
                do {
                    var str = Count.getInt(Count.getColumnIndex("id"))
                    database!!.updateData(str)
                } while (Count.moveToNext())
            } else {
                database!!.insertData(name, 1)
            }
        }

        //settings intent
        SetttingButton.setOnClickListener() {
            var myIntentSetting = Intent(this@user, settingActivity::class.java)
            startActivity(myIntentSetting)
        }
        //history button
        historyButton.setOnClickListener() {
            var myIntenetHistory = Intent(this@user, historyActivity::class.java)
            startActivity(myIntenetHistory)
        }
        //sound button
        SoundButton.setOnClickListener() {
            var myIntentSound = Intent(this@user, SoundActivity::class.java)
            startActivity(myIntentSound)
        }
        //reset functions >>
        buttonReset2.setOnClickListener() {
            count = 0
            textView2.text = count.toString()
            button2.text = "سُبْحَانَ اللَّهِ"
            if (countingCurrentValue != 0 || countingCurrentValue != -1) {
                countingTotalValue = countingCurrentValue
            }
        }
    }

    override fun onResume() {
        super.onResume()
        button2Txt = getSharedPreferences("text2", Context.MODE_PRIVATE)
        buttonTxtEditor = button2Txt!!.edit()
        button2.text = button2Txt!!.getString("text2", "سبح الله").toString()
    }

    // notifications
    val notifcationChannelId = "1234"

    @RequiresApi(Build.VERSION_CODES.M)
    fun showNotification(title: String, text: String) {

        val myNotificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this@user, String())

        val myIntentNotifcation = Intent(this@user, user::class.java)
        myIntentNotifcation.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val resultPendingIntent = PendingIntent.getActivities(
            this@user, 25,
            arrayOf(myIntentNotifcation),
            PendingIntent.FLAG_IMMUTABLE
        )

        mBuilder.setColor(304000)
        mBuilder.setSmallIcon(R.drawable.ic_baseline_arrow_upward_24)
        mBuilder.setContentTitle(title)
        mBuilder.setContentText(text)
        mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
        mBuilder.setContentIntent(resultPendingIntent)
        mBuilder.setAutoCancel(false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notiChanal = NotificationChannel(
                notifcationChannelId,
                "news",
                NotificationManager.IMPORTANCE_LOW
            )
            mBuilder.setChannelId(notifcationChannelId)
            myNotificationManager.createNotificationChannel(notiChanal)
        }

        myNotificationManager.notify(7, mBuilder.build())
    }

}
