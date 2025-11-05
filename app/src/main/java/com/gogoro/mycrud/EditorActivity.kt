package com.gogoro.mycrud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gogoro.mycrud.data.local.AppDatabase
import com.gogoro.mycrud.data.local.entity.Note
import com.gogoro.mycrud.databinding.ActivityEditorBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class EditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditorBinding
    private val viewModel: EditorViewModel by viewModels()

    private var noteId: Int? = null
    private var createdAt: Long = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ambil id dari intent
        noteId = intent.getIntExtra("NOTE_ID", -1).takeIf { it != -1 }

        // kalau ada, berarti edit
        noteId?.let { id ->
            viewModel.getNoteById(id)
            observeNote()
        } ?: run {
            binding.title.text = "Tambah Catatan"
            binding.etCreatedAt.setText(formatDate(createdAt))
        }

        binding.btnSave.setOnClickListener {
            saveNote()
        }
    }

    private fun observeNote() {
        lifecycleScope.launch {
            viewModel.selectedNote.collect { note ->
                note?.let {
                    binding.title.text = "Edit Catatan"
                    binding.etTitle.setText(it.title)
                    binding.etDesc.setText(it.desc)
                    binding.etCreatedAt.setText(formatDate(it.createdAt))
                    createdAt = it.createdAt
                }
            }
        }
    }

    private fun saveNote() {
        val title = binding.etTitle.text.toString().trim()
        val desc = binding.etDesc.text.toString().trim()

        if (title.isEmpty()) {
            binding.etTitle.error = "Judul wajib diisi"
            return
        }

        val note = Note(
            id = noteId ?: 0,
            title = title,
            desc = desc,
            createdAt = createdAt,
            finished = false
        )

        if (noteId == null) viewModel.insertNote(note)
        else viewModel.updateNote(note)

        finish()
    }

    private fun formatDate(time: Long): String {
        return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(time))
    }
}
