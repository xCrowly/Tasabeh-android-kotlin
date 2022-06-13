package com.learn.button

import android.annotation.SuppressLint
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_history.*

class historyActivity : AppCompatActivity() {

    var HistoryArray = ArrayList<String>()
    var idArray = ArrayList<Int>()
    var HistoryArrayAdapter: ArrayAdapter<String>? = null
    var database: HistoryDataBase? = null
    var alert: AlertDialog.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        database = HistoryDataBase(this, "history.db", null, 1)
        HistoryArrayAdapter = ArrayAdapter(this, R.layout.text_sample, HistoryArray)
        HistoryArrayList.adapter = HistoryArrayAdapter
        readData()

        HistoryArrayList.setOnItemClickListener { parent, view, position, id ->

            alert = AlertDialog.Builder(this)
            alert!!.setTitle("هل تريد الحذف")
            alert!!.setMessage("حذف هذا العنصر؟")
            alert!!.setPositiveButton("نعم") { dialog, which ->
                database!!.removeAtId(idArray[position])
                readData()
                dialog.cancel()
            }
            alert!!.setNegativeButton("لا") { dialog, which ->
                dialog.cancel()
            }
            alert!!.show()
        }

    }

    @SuppressLint("Range")
    fun readData() {
        var cursor: Cursor = database!!.getAllData()
        HistoryArray.clear()
        idArray.clear()
        if (cursor.moveToFirst()) {
            do {
                var str = " ' ${cursor.getString(cursor.getColumnIndex("Tsbe7"))} '  العدد: ${
                    cursor.getInt(cursor.getColumnIndex("Count"))
                }"
                idArray.add(cursor.getInt(cursor.getColumnIndex("id")))
                HistoryArray.add(str)
            } while (cursor.moveToNext())
        }
        HistoryArrayAdapter!!.notifyDataSetChanged()
    }

}