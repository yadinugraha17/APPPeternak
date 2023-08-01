package com.example.myapplication.core.adapter

import android.annotation.SuppressLint
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.core.data.api.response.item.TernakItem
import com.example.myapplication.databinding.ListTernakBinding
import com.squareup.picasso.Picasso

class TernakAdapter : RecyclerView.Adapter<TernakAdapter.LibraryViewHolder>() {
    private var listGovernmentEffort = ArrayList<TernakItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newTernakItem: List<TernakItem>?) {
        if (newTernakItem == null) return
        listGovernmentEffort.clear()
        listGovernmentEffort.addAll(newTernakItem)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class LibraryViewHolder(private val binding: ListTernakBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ternakItem: TernakItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(ternakItem)
            }
            with(binding) {
                tvTernak.text = ternakItem.nama_ternak
                tvRumpun.text = Html.fromHtml(ternakItem.rumpun.nama)
                if (ternakItem.photo!=""){
                    Picasso.get().load(ternakItem.photo)
                        .error(R.drawable.ternak)
                        .into(imageView)
                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val binding =
            ListTernakBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val data = listGovernmentEffort[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listGovernmentEffort.size
    interface OnItemClickCallback {
        fun onItemClicked(news: TernakItem)
        }
}