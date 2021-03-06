package com.umair.archiTemplate2.ui.rooms

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umair.archiTemplate2.R
import com.umair.archiTemplate2.model.room.Room
import kotlinx.android.synthetic.main.item_room.view.*

class RoomDiffCallback : DiffUtil.ItemCallback<Room>() {

    override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem.roomId == newItem.roomId

    }

    override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem.roomId == newItem.roomId
    }
}

class RoomsAdapter(val onConfirmDelete: (id: String) -> Unit, var isSimpleList: Boolean = true) :
    ListAdapter<Room, RoomsAdapter.ViewHolder>(
        RoomDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(
                R.layout.item_room,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pin = getItem(position)

        holder.apply {
            bind(pin, onConfirmDelete, isSimpleList)
            itemView.tag = pin
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Room, onConfirmDelete: (id: String) -> Unit, isSimpleList: Boolean) {

            itemView.ap_1.text = "1. ${item.accessPointTopLeft}"
            itemView.ap_2.text = "2. ${item.accessPointTopRight}"
            itemView.ap_3.text = "3. ${item.accessPointBottomLeft}"
            itemView.ap_4.text = "4. ${item.accessPointBottomRight}"
            itemView.txt_room_name.text = item.roomName
            itemView.txt_assessment.text = item.assessment

            itemView.setOnLongClickListener {
                itemView.btn_delete.visibility = View.VISIBLE
                return@setOnLongClickListener true
            }

            itemView.btn_delete.setOnClickListener {
                onConfirmDelete.invoke(item.roomId)
            }

            if (item.inHere) {
                itemView.img_person_indicator.visibility = View.VISIBLE
            } else {
                itemView.img_person_indicator.visibility = View.INVISIBLE
            }

            if (isSimpleList) {
                itemView.txt_assessment.visibility = View.GONE
                itemView.img_person_indicator.visibility = View.GONE
            } else {
                itemView.txt_assessment.visibility = View.VISIBLE
            }
        }
    }
}
