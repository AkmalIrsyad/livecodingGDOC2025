package com.gogoro.mycrud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gogoro.mycrud.data.local.AppDatabase

class EditorActivity : AppCompatActivity() {
    private lateinit var fullName: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var btnSave: Button
    private lateinit var database: AppDatabase
    private lateinit var titleText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        // Inisialisasi view
        fullName = findViewById(R.id.full_name)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        btnSave = findViewById(R.id.btn_save)
        titleText = findViewById(R.id.title)

        database = AppDatabase.getInstance(applicationContext)

        // Ambil data intent
        val extras = intent.extras
        if (extras != null) {
            val id = extras.getInt("id", 0)
            val user = database.userDao().get(id)

            // Ubah teks title jadi "Edit Data Pengguna"
            titleText.text = "Edit Data Pengguna"

            // Prefill data ke form
            fullName.setText(user.fullName)
            email.setText(user.email)
            phone.setText(user.phone)

            // Klik tombol simpan → update data
            btnSave.setOnClickListener {
                if (fullName.text.isNotEmpty() && email.text.isNotEmpty() && phone.text.isNotEmpty()) {
                    database.userDao().update(
                        User(
                            id,
                            fullName = fullName.text.toString(),
                            email = email.text.toString(),
                            phone = phone.text.toString()
                        )
                    )
                    finish()
                } else {
                    Toast.makeText(this, "Mohon lengkapi data", Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            // Mode tambah data baru
            titleText.text = "Tambah Data Pengguna"

            btnSave.setOnClickListener {
                if (fullName.text.isNotEmpty() && email.text.isNotEmpty() && phone.text.isNotEmpty()) {
                    database.userDao().insertAll(
                        User(
                            null,
                            fullName = fullName.text.toString(),
                            email = email.text.toString(),
                            phone = phone.text.toString()
                        )
                    )
                    finish()
                } else {
                    Toast.makeText(this, "Mohon lengkapi data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
