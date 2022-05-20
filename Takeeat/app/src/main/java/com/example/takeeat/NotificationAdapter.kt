package com.example.takeeat

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class NotificationAdapter(var data: LiveData<ArrayList<NotificationItem>>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent,false)){
        val itemText: TextView
        init {
            itemText = itemView.findViewById(R.id.notificationTextView)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {
        data.value!!.get(position).let { item ->
            with(holder) {
                if (data.value!!.get(position).refItemName != null)
                    itemText.text = data.value!!.get(position).refItemName + "의 유통기한이 얼마 남지 않았습니다!"
            }
        }
    }

    override fun getItemCount(): Int {
        return data.value!!.size
    }
}