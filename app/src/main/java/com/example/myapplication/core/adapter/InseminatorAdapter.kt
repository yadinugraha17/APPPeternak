package com.example.myapplication.core.adapter

import android.annotation.SuppressLint
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.core.data.api.response.item.FindItem
import com.example.myapplication.core.data.api.response.item.TernakItem
import com.example.myapplication.databinding.ListInseminatorBinding
import com.example.myapplication.databinding.ListTernakBinding
import com.squareup.picasso.Picasso

class InseminatorAdapter : RecyclerView.Adapter<InseminatorAdapter.LibraryViewHolder>() {
    private var listGovernmentEffort = ArrayList<FindItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newTernakItem: List<FindItem>?) {
        if (newTernakItem == null) return
        listGovernmentEffort.clear()
        listGovernmentEffort.addAll(newTernakItem)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class LibraryViewHolder(private val binding: ListInseminatorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(InseminatorItem: FindItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(InseminatorItem)
            }
            with(binding) {
                tvInseminatorName.text = InseminatorItem.name
                tvNoInseminator.text = InseminatorItem.no_hp

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val binding =
            ListInseminatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val data = listGovernmentEffort[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listGovernmentEffort.size
    interface OnItemClickCallback {
        fun onItemClicked(news: FindItem)
        }
}