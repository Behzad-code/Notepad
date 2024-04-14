package com.behzad.notepad.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.behzad.notepad.R

class DialogManagement {
    companion object{
        fun loginDialog(activity : Activity){
            val inflator = LayoutInflater.from(activity)
            val view : View = inflator.inflate(R.layout.login_layout,null)
            val dialog = AlertDialog.Builder(activity)
            dialog.setView(view)
            dialog.show()
            var edtUsername = view.findViewById<AppCompatEditText>(R.id.edt_username)
            var edtPassword = view.findViewById<AppCompatEditText>(R.id.edt_password)
            var btnLogin = view.findViewById<AppCompatButton>(R.id.btn_login)

            btnLogin.setOnClickListener {
                Toast.makeText(activity,"Hi Your Are Logged",Toast.LENGTH_SHORT).show()
            }

        }
    }
}