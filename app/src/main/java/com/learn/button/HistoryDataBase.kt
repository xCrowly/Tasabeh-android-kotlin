package com.learn.button

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HistoryDataBase : SQLiteOpenHelper {
    constructor(
        context: Context?,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : super(context, name, factory, version)

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE History (id INTEGER PRIMARY KEY AUTOINCREMENT, Tsbe7 TEXT, Count INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertData(Tsbe7: String?, Count: Int) {
        var Db = writableDatabase
        Db.execSQL("INSERT INTO History (Tsbe7, Count) VALUES ('$Tsbe7', $Count)")
    }

    fun getAllData(): Cursor {
        return readableDatabase.rawQuery("SELECT Tsbe7,Count,id FROM History", null)
    }

    fun updateData(Id: Int) {
        var Db = writableDatabase
        Db.execSQL("UPDATE History set Count = Count+1 WHERE id =$Id")
    }

    fun getId(tsbe7: String): Cursor {
        return readableDatabase.rawQuery("SELECT id FROM History WHERE Tsbe7='$tsbe7'", null)
    }

    fun removeAtId(Id: Int) {
        var Db = writableDatabase
        Db.execSQL("DELETE FROM History WHERE id =$Id")
    }

}