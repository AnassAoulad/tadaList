package com.example.tasklist.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.tasklist.R
import com.example.tasklist.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val editTitle = findViewById<EditText>(R.id.editTitle)
        val editDescription = findViewById<EditText>(R.id.editDescription)
        val button = findViewById<ImageButton>(R.id.sendButton)

        button.setOnClickListener(){
            val newTask = Task(id = UUID.randomUUID().toString(), title = editTitle.text.toString(), description = editDescription.text.toString() )
            intent.putExtra("task", newTask)
            // Instanciation d'un nouvel objet [Task]
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}