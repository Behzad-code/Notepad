package com.behzad.notepad.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.behzad.notepad.R
import com.behzad.notepad.database.NoteDbAdapter
import com.behzad.notepad.models.Note
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class AddNoteActivity : AppCompatActivity() {

    lateinit var edtTitle: AppCompatEditText
    lateinit var edtDescription: AppCompatEditText
    lateinit var btnDate: AppCompatButton
    lateinit var btnTime: AppCompatButton
    lateinit var btnAdd: AppCompatButton
    lateinit var noteDbAdapter: NoteDbAdapter
    lateinit var calender: Calendar
    var currentDate: String = ""
    var currentTime: String = ""
    lateinit var bundle: Bundle
    var type: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        edtTitle = findViewById(R.id.edt_title)
        edtDescription = findViewById(R.id.edt_description)
        btnDate = findViewById(R.id.btn_date)
        btnTime = findViewById(R.id.btn_time)
        btnAdd = findViewById(R.id.btn_add_note)
        noteDbAdapter = NoteDbAdapter(applicationContext)
        calender = Calendar.getInstance()
        bundle = intent.extras!!
        type = bundle.getInt("type")
        if (type == 1) {
            btnAdd.setText("Add Note")
        } else {
            btnAdd.setText("Edit Note")
            edtTitle.setText(bundle.getString("title"))
            edtDescription.setText(bundle.getString("description"))
            currentDate = bundle.getString("date").toString()
            currentTime = bundle.getString("time").toString()

            btnDate.setText(currentDate)
            btnTime.setText(currentTime)

        }

        btnAdd.setOnClickListener {
            val title = edtTitle.text.toString()
            val description = edtDescription.text.toString()
            val note = Note(
                id = 0,
                title = title,
                descriprion = description,
                date = currentDate,
                time = currentTime
            )

            if (type == 1) {
                val result = noteDbAdapter.insertNote(note)

                if (result > 0) {
                    Snackbar.make(it, "Note Added Successfully", Snackbar.LENGTH_LONG).show()

                } else {
                    Snackbar.make(it, "Error in insert note", Snackbar.LENGTH_LONG).show()

                }

            } else {
                val noteId = bundle.getInt("noteId")
                note.id = noteId
                val result = noteDbAdapter.editNote(note)
                if (result > 0) {
                    Snackbar.make(it, "note Edited Successfully", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(it, "Error in editing note", Snackbar.LENGTH_LONG).show()


                }

            }

            val result = noteDbAdapter.insertNote(note)

            if (result > 0) {
                Snackbar.make(it, "Note Added Successfully", Snackbar.LENGTH_LONG).show()

            } else {
                Snackbar.make(it, "Error in insert note", Snackbar.LENGTH_LONG).show()

            }
        }
        btnDate.setOnClickListener {

            val currentYear = calender.get(Calendar.YEAR)
            val currentMonth = calender.get(Calendar.MONTH)
            val currentDay = calender.get(Calendar.DAY_OF_MONTH)

            var dateDialog =
                DatePickerDialog(this@AddNoteActivity, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
                        currentDate = "$year/$month/$day"
                        btnDate.setText(currentDate)

                    }
                }, currentYear, currentMonth, currentDay)
            dateDialog.show()
        }
        btnTime.setOnClickListener {

            var currentHour = calender.get(Calendar.HOUR)
            var currentMinute = calender.get(Calendar.MINUTE)

            var timeDialog = TimePickerDialog(
                this@AddNoteActivity, object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
                        currentTime = "$hour:$minute"
                        btnTime.setText(currentTime)

                    }
                },
                currentHour, currentMinute, true
            )
            timeDialog.show()
        }
    }
}