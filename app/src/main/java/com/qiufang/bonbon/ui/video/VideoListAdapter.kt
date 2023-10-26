package com.qiufang.bonbon.ui.video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.card.MaterialCardView
import com.qiufang.bonbon.R
import com.qiufang.bonbon.ui.gallery.Gallery

class VideoListAdapter :RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {

    lateinit var videoList : List<Video>
    private var mItemClickListener : OnItemClickListener? = null

    fun setData(list:List<Video>){
        videoList = list
    }

    inner class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        val videoCover : ImageView =  view.findViewById(R.id.img_video_cover)
        val videoTitle : TextView = view.findViewById(R.id.txt_video_title)
        val item : MaterialCardView = view.findViewById(R.id.layout_videoList)
    }

    fun setListener(listener: OnItemClickListener){
        mItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video : Video =  videoList[position]
        holder.videoCover.load(video.cover){
            placeholder(R.drawable.notfound)  //设置占位图
            //设置远角变换
            transformations(RoundedCornersTransformation(8f))
        }
        holder.videoTitle.text = video.title
        holder.item.setOnClickListener {
            mItemClickListener?.onItemClick(videoList[position])
        }
    }

    override fun getItemCount() = videoList.size

    interface OnItemClickListener{
        fun onItemClick(video: Video) {
        }
    }
}