package com.cubivue.inlogic.ui.accessPoint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cubivue.inlogic.R
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.utils.DateTimeUtils
import kotlinx.android.synthetic.main.item_access_points.view.*

class AccessPointDiffCallback : DiffUtil.ItemCallback<AccessPoint>() {

    override fun areItemsTheSame(oldItem: AccessPoint, newItem: AccessPoint): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: AccessPoint, newItem: AccessPoint): Boolean {
        return oldItem.id == newItem.id
    }
}

class AccessPointsAdapter :
    ListAdapter<AccessPoint, AccessPointsAdapter.ViewHolder>(
        AccessPointDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(
                R.layout.item_access_points,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pin = getItem(position)

        holder.apply {
            bind(pin)
            itemView.tag = pin
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: AccessPoint) {

            itemView.txt_access_point_name.text = item.name
            itemView.txt_scan_time.text = DateTimeUtils.getReadableTime(item.scanTime)
            itemView.txt_strength.text = item.strength.toString()
        }
    }
}
