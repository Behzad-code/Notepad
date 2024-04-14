package com.behzad.notepad.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.behzad.notepad.R
import com.behzad.notepad.activities.AddNoteActivity
import com.behzad.notepad.database.NoteDbAdapter
import com.behzad.notepad.models.Note

class NotesAdapter(mcontext: Activity, data: MutableList<Note>) :
    RecyclerView.Adapter<NotesAdapter.NoteVH>() {

    var notesList = data
    var context = mcontext
    lateinit var noteDbAdapter: NoteDbAdapter


    class NoteVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle = itemView.findViewById<AppCompatTextView>(R.id.txt_title)
        var txtDescription = itemView.findViewById<AppCompatTextView>(R.id.txt_description)
        var imgEdit = itemView.findViewById<AppCompatImageView>(R.id.img_edit)
        var imgDelete = itemView.findViewById<AppCompatImageView>(R.id.img_delete)
        var imgShare = itemView.findViewById<AppCompatImageView>(R.id.img_share)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
        noteDbAdapter = NoteDbAdapter(context)
        val inflator = LayoutInflater.from(context)
        val view = inflator.inflate(R.layout.notes_row, null)
        return NoteVH(view)

    }

    override fun getItemCount(): Int {
        return notesList.size

    }

    override fun onBindViewHolder(holder: NoteVH, position: Int) {

        val note = notesList.get(position)
        holder.txtTitle.setText(note.title)
        holder.txtDescription.setText(note.descriprion)

        holder.imgShare.setOnClickListener {

            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT, note.title)
            context.startActivity(Intent.createChooser(intent, "share"))

        }

        holder.imgDelete.setOnClickListener {

            noteDbAdapter.deleteNoteById(note.id)
            notesList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, notesList.size)

        }

        holder.imgEdit.setOnClickListener {
            val intent = Intent(context, AddNoteActivity::class.java)
            intent.putExtra("type",2)
            intent.putExtra("id", note.id)
            intent.putExtra("title", note.title)
            intent.putExtra("description", note.descriprion)
            intent.putExtra("date", note.date)
            intent.putExtra("time", note.time)
            context.startActivity(intent)

        }
    }
}