package com.example.vkmusicapp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class PhotoDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)

        val imageView: ImageView = findViewById(R.id.imageView)
        val photoUrl = intent.getStringExtra("PHOTO_URL")

        Glide.with(this)
            .load(photoUrl)
            .into(imageView)

        imageView.setOnClickListener {
            finish() // Закрыть активити при нажатии на изображение
        }
    }
}
