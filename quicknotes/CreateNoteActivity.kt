package com.example.aubreymercado_comp304lab1_ex1.quicknotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

class CreateNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateNoteScreen(
                onSaveClick = { title, content ->
                    val resultIntent = Intent().apply {
                        putExtra("note_title", title)
                        putExtra("note_content", content)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                },
                onBackClick = { finish() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(onSaveClick: (String, String) -> Unit, onBackClick: () -> Unit) {
    val title = remember { mutableStateOf("") }
    val content = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Note") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    label = { Text("Title") },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = content.value,
                    onValueChange = { content.value = it },
                    label = { Text("Content") },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(onClick = {
                    onSaveClick(title.value, content.value)
                }) {
                    Text("Save Note")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CreateNoteScreenPreview() {
    CreateNoteScreen(onSaveClick = { _, _ -> }, onBackClick = {})
}
