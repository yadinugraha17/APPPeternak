package com.example.myapplication.core.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.core.data.api.response.item.BookItem
import com.example.myapplication.databinding.ListBookBinding
import com.squareup.picasso.Picasso

class BookAdapter : RecyclerView.Adapter<BookAdapter.LibraryViewHolder>() {
    private var listGovernmentEffort = ArrayList<BookItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newTernakItem: List<BookItem>?) {
        if (newTernakItem == null) return
        listGovernmentEffort.clear()
        listGovernmentEffort.addAll(newTernakItem)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class LibraryViewHolder(private val binding: ListBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(BookItem: BookItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(BookItem)
            }
            with(binding) {
                tvBook.text = BookItem.title
                if (BookItem.cover!=""){
                    Picasso.get().load(BookItem.cover)
                        .error(R.drawable.ternak)
                        .into(IVBook)
                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val binding =
            ListBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val data = listGovernmentEffort[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listGovernmentEffort.size
    interface OnItemClickCallback {
        fun onItemClicked(news: BookItem)
    }
}
