package com.secondweek.photogallery.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.secondweek.photogallery.ImageFullActivity
import com.secondweek.photogallery.R
import com.secondweek.photogallery.model.Image
import com.squareup.picasso.Picasso

class ImageAdapter(private var context: Context, private  var imageList : ArrayList<Image> )
    :RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.image_row, parent, false)

        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentImage = imageList[position]

        Glide.with(context)
            .load(currentImage.imagePath)
            .apply(RequestOptions().centerCrop())
            .into(holder.image)

        holder.image?.setOnClickListener {
            val intent = Intent(context, ImageFullActivity::class.java)
            intent.putExtra("path", currentImage.imagePath)
            intent.putExtra("name", currentImage.imageName)
            context.startActivity(intent)

        }


    }

    class ImageViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        var image: ImageView?=null
        init {
            image = itemView.findViewById(R.id.image_row_item)
        }


    }
}
