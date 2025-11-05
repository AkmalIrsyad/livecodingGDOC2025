package com.gogoro.mycrud

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.gogoro.mycrud.adapter.NoteAdapter
import com.gogoro.mycrud.data.local.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private var list = mutableListOf<User>()
    private lateinit var adapter: NoteAdapter
    private lateinit var database: AppDatabase




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        fab = findViewById(R.id.fab)
        adapter = NoteAdapter(list)
        adapter.setDialog(object : NoteAdapter.Dialog{
            // Membuat dialog view ketika item di klik
            override fun onClick(position: Int) {
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle(list[position].fullName)
                dialog.setItems(R.array.items_option, DialogInterface.OnClickListener{ dialog,which ->
                    if (which==0){
                        // coding ubah
                        val intent = Intent(this@MainActivity, EditorActivity::class.java)
                        intent.putExtra("id", list[position].uid)
                        startActivity(intent)
                    } else if(which==1){
                        // coding hapus
                        database.userDao().delete(list[position])
                        getData()
                    }
                })
                val dialogView = dialog.create()
                dialogView.show()
            }

        })

        database = AppDatabase.getInstance(applicationContext)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext,VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))
        fab.setOnClickListener {
            startActivity(Intent(this, EditorActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getData(){
        list.clear()
        list.addAll(database.userDao().getAll())
        adapter.notifyDataSetChanged()
    }
}
