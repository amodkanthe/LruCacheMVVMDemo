package com.example.lrucachemvvmdemo.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.lrucachemvvmdemo.databinding.RowCacheImageBinding
import androidx.databinding.BindingAdapter




class ImageCacheAdapter(private val items: List<Bitmap>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ImageCacheViewHolder(
            RowCacheImageBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ImageCacheViewHolder)?.bind(
            items?.get(
                position
            ) as? Bitmap
        )
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }
    inner class ImageCacheViewHolder(val binding: RowCacheImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Bitmap?) {
            binding?.model = model
            binding?.executePendingBindings()
        }
    }
}

@BindingAdapter("bind:imageBitmap")
fun loadImage(iv: ImageView, bitmap: Bitmap?) {
    iv.setImageBitmap(bitmap)
}