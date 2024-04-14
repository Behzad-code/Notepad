package com.behzad.notepad

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.behzad.notepad.activities.AboutActivity
import com.behzad.notepad.activities.AddNoteActivity
import com.behzad.notepad.adapter.NotesAdapter
import com.behzad.notepad.database.MyDatabase
import com.behzad.notepad.database.NoteDbAdapter
import com.behzad.notepad.models.Note
import com.behzad.notepad.ui.DialogManagement
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var navMenu: NavigationView
    lateinit var drawer: DrawerLayout
    lateinit var fabAdd: FloatingActionButton
    lateinit var recyclerNotes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myDatabase = MyDatabase(applicationContext)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        navMenu = findViewById(R.id.nav_menu)
        drawer = findViewById(R.id.drawer)
        fabAdd = findViewById(R.id.fab_add)
        recyclerNotes = findViewById(R.id.recycler_notes)

        var drawer = ActionBarDrawerToggle(
            this@MainActivity, drawer, toolbar, R.string.open, R.string.close
        )
        drawer.syncState()

        navMenu.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_add -> {

                    true
                }

                R.id.item_setting -> {

                    true
                }

                R.id.item_login -> {

                    DialogManagement.loginDialog(this@MainActivity)
                    true
                }

                else -> {

                    false
                }
            }
        }

        fabAdd.setOnClickListener {
            val intent = Intent(applicationContext, AddNoteActivity::class.java)
            intent.putExtra("type", 1)
            startActivity(intent)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_about -> {

                val intent = Intent(applicationContext, AboutActivity::class.java)
                startActivity(intent)

            }

            R.id.item_contact -> {

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:09126302271"))
                startActivity(intent)

            }

            R.id.item_exit -> {
                var dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle(R.string.exit)
                dialog.setIcon(R.drawable.exit)
                dialog.setMessage(getString(R.string.are_you_sure_to_exit))
                dialog.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        finishAffinity()
                    }

                })

                dialog.setNeutralButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {

                    }

                })
                dialog.show()

            }

            R.id.item_search -> {

            }

            R.id.item_web -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Behzad-code"))
                startActivity(intent)

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        val dbAdapter = NoteDbAdapter(applicationContext)
        val adapter = NotesAdapter(this@MainActivity, dbAdapter.showAllNotes())
        recyclerNotes.adapter = adapter

        recyclerNotes.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
    }

}