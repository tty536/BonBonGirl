package com.qiufang.bonbon.ui.photo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.qiufang.bonbon.R
import com.qiufang.bonbon.databinding.ActivityPhotoBinding
import com.qiufang.bonbon.ui.music.MusicAdapter
import com.qiufang.bonbon.utils.LogUtil

class PhotoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPhotoBinding
    private lateinit var photoVieModel : PhotoViewModel
    private lateinit var galleryId : String
    private lateinit var galleryName : String

    private lateinit var photoList : RecyclerView
    private lateinit var frameBig : FrameLayout
    private lateinit var frameList : FrameLayout
    private lateinit var imgBig : ImageView
    lateinit var adapter : PhotoAdapter
    private val TAG : String = "PhotoActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        LogUtil.d(TAG,"onCreate")
        photoVieModel = ViewModelProvider(this).get(PhotoViewModel::class.java)


        photoList = binding.photo
        frameBig = binding.frameBig
        frameList = binding.frameList
        imgBig = binding.imgBig

        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        photoList.layoutManager = layoutManager

        galleryId = intent.getStringExtra("gallery_id").toString()
        galleryName = intent.getStringExtra("gallery_name").toString()
//        albumId = intent.getStringExtra("album_id").toString()

        this.title = galleryName
        photoVieModel.getPhoto(galleryId,galleryName)
        photoVieModel.photoList.observe(this, Observer {
            val photoListData = it ?: return@Observer
            LogUtil.d(TAG,photoListData.toString())
            adapter = PhotoAdapter()
            adapter.setData(photoListData)
            adapter.setListener(object :PhotoAdapter.OnItemClickListener{
                override fun onItemClick(photo: Photo) {
                    super.onItemClick(photo)
                    frameList.visibility = View.GONE
                    frameBig.visibility = View.VISIBLE
                    imgBig.load(photo.url)
                }

            })
            photoList.adapter = adapter
        })
        frameBig.setOnClickListener{
            frameList.visibility = View.VISIBLE
            frameBig.visibility = View.GONE
        }

    }
}