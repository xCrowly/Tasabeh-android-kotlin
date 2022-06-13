package com.learn.button

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_setting.*

class settingActivity : AppCompatActivity() {
    var switch1Checked: SharedPreferences? = null
    var switch1CheckedEditor: SharedPreferences.Editor? = null
    var switch2Checked: SharedPreferences? = null
    var switch2CheckedEditor: SharedPreferences.Editor? = null
    var switch3Checked: SharedPreferences? = null
    var switch3CheckedEditor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        switch1Checked = getSharedPreferences("Checked1", Context.MODE_PRIVATE)
        switch1CheckedEditor = switch1Checked!!.edit()
        switch1.isChecked = switch1Checked!!.getBoolean("Checked1", true)

        switch2Checked = getSharedPreferences("Checked2", Context.MODE_PRIVATE)
        switch2CheckedEditor = switch2Checked!!.edit()
        switch2.isChecked = switch2Checked!!.getBoolean("Checked2", true)

        switch3Checked = getSharedPreferences("Checked3", Context.MODE_PRIVATE)
        switch3CheckedEditor = switch3Checked!!.edit()
        switch3.isChecked = switch3Checked!!.getBoolean("Checked3", true)

        switch1.setOnClickListener() {
            if (switch1.isChecked == true) {
                switch1CheckedEditor!!.putBoolean("Checked1", true)
                switch1CheckedEditor!!.apply()

            } else {
                switch1CheckedEditor!!.putBoolean("Checked1", false)
                switch1CheckedEditor!!.apply()
            }
        }
        switch2.setOnClickListener() {
            if (switch2.isChecked == true) {
                switch2CheckedEditor!!.putBoolean("Checked2", true)
                switch2CheckedEditor!!.apply()

            } else {
                switch2CheckedEditor!!.putBoolean("Checked2", false)
                switch2CheckedEditor!!.apply()
            }
        }
        switch3.setOnClickListener() {
            if (switch3.isChecked == true) {
                switch3CheckedEditor!!.putBoolean("Checked3", true)
                switch3CheckedEditor!!.apply()

            } else {
                switch3CheckedEditor!!.putBoolean("Checked3", false)
                switch3CheckedEditor!!.apply()
            }
        }
    }
}