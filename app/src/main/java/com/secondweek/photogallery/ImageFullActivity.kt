package com.secondweek.photogallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.secondweek.photogallery.databinding.ActivityImageFullBinding
import com.squareup.picasso.Picasso

class ImageFullActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageFullBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageFullBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imagePath = intent.getStringExtra("path")
        val imageName = intent.getStringExtra("name")

        supportActionBar?.setTitle(imageName)

        Picasso.get()
            .load(imagePath)
            .into(binding.picture)

//        Glide.with(this)
//            .load(imagePath)
//            .apply(RequestOptions().centerCrop())
//            .into(binding.picture)
    }
}