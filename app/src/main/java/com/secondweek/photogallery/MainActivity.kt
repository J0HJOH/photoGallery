package com.secondweek.photogallery

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.secondweek.photogallery.adapter.ImageAdapter
import com.secondweek.photogallery.databinding.ActivityMainBinding
import com.secondweek.photogallery.model.Image

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var photoRecycler : RecyclerView
    private  lateinit var emptyLayout : LinearLayout
    private lateinit var progressBar : ProgressBar
    private var phoneImages : ArrayList<Image>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        photoRecycler = binding.photoRecycler
        progressBar = binding.progressBar
        emptyLayout =binding.emptyLayout

        binding.cameraBtn.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }


        checkGalleryPermission()

        phoneImages = ArrayList()
        if(phoneImages!!.isEmpty()){
            progressBar.visibility = View.VISIBLE
            phoneImages = getAllImages()
            if(phoneImages == null){
                photoRecycler.visibility = View.GONE
                progressBar.visibility = View.GONE
                emptyLayout.visibility = View.VISIBLE
            }else{
                emptyLayout.visibility = View.GONE
                photoRecycler.adapter = ImageAdapter(this, phoneImages!!)
                photoRecycler.layoutManager = GridLayoutManager(this,3)
                photoRecycler.setHasFixedSize(true)
                progressBar.visibility = View.GONE
            }

        }
    }



    private fun getAllImages(): ArrayList<Image>? {
        val images = ArrayList<Image>()

        val imagesUri =MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA,
        MediaStore.Images.Media.DISPLAY_NAME)
        val cursor = this@MainActivity.contentResolver.query(imagesUri,
            projection, null, null, null)


        try {
            cursor!!.moveToFirst()
            do {
                val image = Image()
                image.imagePath = cursor
                    .getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imageName = cursor.getString(cursor.getColumnIndexOrThrow(
                    MediaStore.Images.Media.DISPLAY_NAME))
                images.add(image)
            }while (cursor.moveToNext())
            cursor.close()
        }catch (e: Exception){
            Toast.makeText(this, "An error occured : $e", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
        return images
    }

    private fun checkGalleryPermission() {
        if(ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@MainActivity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                101
            )
        }
    }

}