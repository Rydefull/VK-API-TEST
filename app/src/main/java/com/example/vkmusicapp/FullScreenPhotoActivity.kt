package com.example.vkmusicapp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class FullScreenPhotoActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_photo)

        imageView = findViewById(R.id.fullscreen_image_view)

        val photoUrl = intent.getStringExtra("PHOTO_URL")
        photoUrl?.let {
            Glide.with(this)
                .load(it)
                .into(imageView)
        }
    }
}
