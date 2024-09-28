package com.example.aubreymercado_comp304lab1_ex1.quicknotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class ViewEditNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val noteId = intent.getIntExtra("note_id", -1)
        val noteTitle = intent.getStringExtra("note_title") ?: ""
        val noteContent = intent.getStringExtra("note_content") ?: ""

        setContent {
            ViewEditNoteScreen(noteId, noteTitle, noteContent, this)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewEditNoteScreen(noteId: Int, initialTitle: String, initialContent: String, activity: Activity) {
    var title by remember { mutableStateOf(initialTitle) }
    var content by remember { mutableStateOf(initialContent) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Edit Note") })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(onClick = {
                    val resultIntent = Intent().apply {
                        putExtra("note_id", noteId)
                        putExtra("note_title", title)
                        putExtra("note_content", content)
                    }
                    activity.setResult(Activity.RESULT_OK, resultIntent)
                    activity.finish()
                }) {
                    Text("Save")
                }
            }
        }
    )
}
