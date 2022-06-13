package com.learn.button

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_layout2.*

class azkar : AppCompatActivity() {

    var button2Txt: SharedPreferences? = null
    var buttonTxtEditor: SharedPreferences.Editor? = null
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

    var adapterArray: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout2)

        button2Txt = getSharedPreferences("text2", Context.MODE_PRIVATE)
        buttonTxtEditor = button2Txt!!.edit()
        adapterArray = ArrayAdapter(this, R.layout.text_sample, nameArray)
        listView.adapter = adapterArray

        listView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                buttonTxtEditor!!.putString("text2", nameArray.elementAt(position)).toString()
                buttonTxtEditor!!.apply()

                //to resume activity without starting oncreate fun
                val openMainActivity = Intent(this@azkar, user::class.java)
                openMainActivity.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivityIfNeeded(openMainActivity, 0)
            }
        }

        /*
        listView.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
                ): Boolean {
                //nameArr.removeAt(position)
                //adapterArr!!.notifyDataSetChanged()
                return true
            }
        }*/

        buttonLayout2.setOnClickListener() {
            nameArray.add(editTextLayout2.text.toString())
            adapterArray!!.notifyDataSetChanged()
        }

        var timer = object : CountDownTimer(1000, 500) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                finish()
            }
        }
        timer.start()

    }
}

