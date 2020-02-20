package com.cubivue.inlogic.ui.roomMapper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.cubivue.inlogic.R
import kotlinx.android.synthetic.main.item_single_selection.view.*

/**
 * Created by Umair Adil on 06/03/2017.
 */

class SingleSelectionDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem

    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class SingleSelectionAdapter(
    val onSelection: (selected: String) -> Unit,
    private var selected: String = ""
) :
    ListAdapter<String, SingleSelectionAdapter.ViewHolder>(
        SingleSelectionDiffCallback()
    ) {

    fun setSelection(selected: String) {
        this.selected = selected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(
                R.layout.item_single_selection,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pin = getItem(position)

        holder.apply {
            bind(pin, onSelection, selected)
            itemView.tag = pin
        }
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: String, onSelection: (selected: String) -> Unit, selected: String) {

            itemView.text.text = item

            itemView.text.setOnClickListener {
                onSelection.invoke(item)
            }

            if (selected.isNotEmpty()) {
                itemView.text.isChecked = selected == item
            }
        }
    }
}
