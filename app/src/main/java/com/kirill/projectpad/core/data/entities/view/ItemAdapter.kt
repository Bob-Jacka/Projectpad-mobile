package com.kirill.projectpad.core.data.entities.view

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kirill.projectpad.R
import com.kirill.projectpad.pages.MainActivity.Companion.block_view

class ItemAdapter(private val items: MutableList<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnCreateContextMenuListener {

        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvType: TextView = view.findViewById(R.id.tvType)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)

        init {
            view.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu,
            v: View,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            block_view = v
            menu.setHeaderTitle("Project actions:")
            MenuInflater(v.context).inflate(R.menu.context_menu, menu)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.tvType.text = item.type
        holder.tvDescription.text = item.description
    }

    override fun getItemCount() = items.size
}