package com.behzad.notepad.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.behzad.notepad.models.Note

class NoteDbAdapter(context: Context?) : MyDatabase(context) {


    fun insertNote(note: Note): Long {

        val database: SQLiteDatabase = writableDatabase

        val cv = ContentValues()
        cv.put("title", note.title)
        cv.put("description", note.descriprion)
        cv.put("dateNote", note.date)
        cv.put("timeNote", note.time)

        return database.insert("tbl_note", null, cv)

    }

    fun editNote(note: Note): Int {

        val database: SQLiteDatabase = writableDatabase

        val cv = ContentValues()
        cv.put("title", note.title)
        cv.put("description", note.descriprion)
        cv.put("dateNote", note.date)
        cv.put("timeNote", note.time)

        return database.update("tbl_note", cv,"id?", arrayOf(note.id.toString()))

    }

    @SuppressLint("Range")
    fun showAllNotes(): MutableList<Note> {
        val db: SQLiteDatabase = readableDatabase

        val query = "select * from tbl_note"

        val cursor: Cursor = db.rawQuery(query, null)

        val myNotes = mutableListOf<Note>()

        while (cursor.moveToNext()) {

            val id = cursor.getInt(0)
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val description = cursor.getString(cursor.getColumnIndex("description"))
            val date = cursor.getString(cursor.getColumnIndex("dateNote"))
            val time = cursor.getString(cursor.getColumnIndex("timeNote"))

            val note = Note(id, title, description, date, time)
            myNotes.add(note)

        }
        return myNotes
    }

    fun deleteAllNotes() {

        val db: SQLiteDatabase = writableDatabase
        db.delete("tbl_note", null, null)

    }

    fun deleteNoteById(id: Int): Int {

        val db: SQLiteDatabase = writableDatabase
        return db.delete("tbl_note", "id=?", arrayOf("$id"))

    }
}