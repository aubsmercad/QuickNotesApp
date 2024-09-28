package com.example.aubreymercado_comp304lab1_ex1.quicknotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh

class HomeActivity : ComponentActivity() {
    private val notes = mutableListOf<Note>()
    private val createNoteLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val noteId = result.data?.getIntExtra("note_id", -1) ?: -1
            val title = result.data?.getStringExtra("note_title") ?: ""
            val content = result.data?.getStringExtra("note_content") ?: ""

            if (noteId >= 0) {
                notes[noteId] = Note(noteId, title, content)
            } else {
                notes.add(Note(notes.size, title, content))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var notesState by remember { mutableStateOf(notes.toList()) }

            HomeScreen(
                notes = notesState,
                onAddNoteClick = {
                    createNoteLauncher.launch(Intent(this, CreateNoteActivity::class.java))
                },
                onRefreshClick = {
                    notesState = notes.toList()
                },
                onEditNoteClick = { note ->
                    val editIntent = Intent(this, ViewEditNoteActivity::class.java).apply {
                        putExtra("note_id", note.id)
                        putExtra("note_title", note.title)
                        putExtra("note_content", note.content)
                    }
                    createNoteLauncher.launch(editIntent)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    notes: List<Note>,
    onAddNoteClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onEditNoteClick: (Note) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Notes") },
                actions = {
                    IconButton(onClick = onRefreshClick) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh Notes")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNoteClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        },
        content = { paddingValues ->
            NoteList(
                notes = notes,
                onEditNoteClick = onEditNoteClick,
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}

@Composable
fun NoteList(
    notes: List<Note>,
    onEditNoteClick: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp)) {
        items(notes) { note ->
            NoteItem(note, onClick = { onEditNoteClick(note) })
        }
    }
}

@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.padding(bottom = 8.dp)) {
        Text(text = "${note.title}: ${note.content}")
    }
}
