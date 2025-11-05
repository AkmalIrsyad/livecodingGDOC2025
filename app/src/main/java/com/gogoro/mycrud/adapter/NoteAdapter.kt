package com.gogoro.mycrud.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gogoro.mycrud.R
import com.gogoro.mycrud.data.local.entity.Note
import com.gogoro.mycrud.databinding.NoteItemBinding
import java.text.SimpleDateFormat
import java.util.Date

class NoteAdapter(
    private val onItemClick: ((Note) -> Unit)? = null
) : ListAdapter<Note, NoteAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(old: Note, new: Note): Boolean = old.id == new.id
            override fun areContentsTheSame(old: Note, new: Note): Boolean = old == new
        }
    }

    inner class VH(
        private val binding: NoteItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Note) = with(binding) {
            tvTitle.text = item.title
            tvDesc.text = item.desc
            tvCreatedAt.text = item.createdAt.toReadableDate()
            cbFinished.isChecked = item.finished
            cbFinished.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    tvTitle.paintFlags = tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvDesc.paintFlags = tvDesc.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    tvTitle.paintFlags = tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    tvDesc.paintFlags = tvDesc.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
            root.setOnClickListener { onItemClick?.invoke(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}

private fun Long.toReadableDate(): String {
    return SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault()).format(Date(this))
}