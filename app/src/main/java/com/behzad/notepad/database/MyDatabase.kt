package com.behzad.notepad.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class MyDatabase(
    context: Context?,
) : SQLiteOpenHelper(context, "Note.db", null, 1) {
    override fun onCreate(databse: SQLiteDatabase?) {

        val query = "create table tbl_note (id integer PRIMARY KEY AUTOINCREMENT, " +
                "title varchar(100) , description Text , dateNote  varchar(100), timeNote varchar(100) )"

        databse?.execSQL(query)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}