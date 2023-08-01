package com.example.myapplication.core.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.core.data.api.response.item.HistoryItem
import com.example.myapplication.core.data.api.response.item.NotifikasiItem
import com.example.myapplication.databinding.ListHistoryBinding
import com.example.myapplication.databinding.ListNotifikasiBinding

class NotifikasiAdapter : RecyclerView.Adapter<NotifikasiAdapter.LibraryViewHolder>() {
    private var listGovernmentEffort = ArrayList<NotifikasiItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newTernakItem: List<NotifikasiItem>?) {
        if (newTernakItem == null) return
        listGovernmentEffort.clear()
        listGovernmentEffort.addAll(newTernakItem)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class LibraryViewHolder(private val binding: ListNotifikasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(NotifikasiItem: NotifikasiItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(NotifikasiItem)
            }
            with(binding) {
                tvNotif.text = NotifikasiItem.notif
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val binding =
            ListNotifikasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val data = listGovernmentEffort[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listGovernmentEffort.size
    interface OnItemClickCallback {
        fun onItemClicked(news: NotifikasiItem)
    }
}