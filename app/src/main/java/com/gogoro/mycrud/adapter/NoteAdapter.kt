package com.gogoro.mycrud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gogoro.mycrud.R
import com.gogoro.mycrud.data.local.entity.Note
import java.text.SimpleDateFormat

class NoteAdapter(
    private val notes: MutableList<Note>
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        val tvCreatedAt: TextView = itemView.findViewById(R.id.tvCreatedAt)
        val cbFinished: CheckBox = itemView.findViewById(R.id.cbFinished)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = notes[position]

        holder.tvTitle.text = item.title
        holder.tvDesc.text = item.desc
        holder.tvCreatedAt.text = SimpleDateFormat("dd MMM yyyy").format(item.createdAt)
        holder.cbFinished.isChecked = item.finished
    }

    fun setData(newList: List<Note>) {
        notes.clear()
        notes.addAll(newList)
        notifyDataSetChanged()
    }
}
