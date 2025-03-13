package com.example.vkmusicapp

import android.widget.ImageView
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter(private val photos: List<PhotoItem>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageView) // Изменить на findViewById
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoUrl = photos[position].sizes.last().url // Используем последний размер для загрузки
        Glide.with(holder.itemView.context)
            .load(photoUrl)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, FullScreenPhotoActivity::class.java)
            intent.putExtra("PHOTO_URL", photoUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = photos.size
}
