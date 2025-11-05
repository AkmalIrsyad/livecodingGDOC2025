package com.gogoro.mycrud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gogoro.mycrud.R
import com.gogoro.mycrud.data.local.entity.Note

class NoteAdapter(var list: List<Note>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    fun setDialog(dialog: Dialog){
        this.dialog = dialog

    }

    private lateinit var dialog: Dialog

    interface Dialog {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.row_user,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
//        holder.fullName.text = list[position].fullName
//        holder.email.text = list[position].email
//        holder.phone.text = list[position].phone

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fullName: TextView
        var email: TextView
        var phone: TextView

        init {
            fullName = view.findViewById(R.id.full_name)
            email = view.findViewById(R.id.email)
            phone = view.findViewById(R.id.phone)
            view.setOnClickListener {
                dialog.onClick(layoutPosition)
            }
        }
    }
}
