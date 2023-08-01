package com.example.myapplication.core.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.core.data.api.response.item.HistoryItem
import com.example.myapplication.databinding.ListHistoryBinding
import com.example.myapplication.databinding.ListInseminatorBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.LibraryViewHolder>() {
    private var listGovernmentEffort = ArrayList<HistoryItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newTernakItem: List<HistoryItem>?) {
        if (newTernakItem == null) return
        listGovernmentEffort.clear()
        listGovernmentEffort.addAll(newTernakItem)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class LibraryViewHolder(private val binding: ListHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(HistoryItem: HistoryItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(HistoryItem)
            }
            with(binding) {
                tvSapi.text = HistoryItem.ternak.no_regis
                tvNamainseminator.text = HistoryItem.insiminator.nama

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val binding =
            ListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val data = listGovernmentEffort[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listGovernmentEffort.size
    interface OnItemClickCallback {
        fun onItemClicked(news: HistoryItem)
    }
}