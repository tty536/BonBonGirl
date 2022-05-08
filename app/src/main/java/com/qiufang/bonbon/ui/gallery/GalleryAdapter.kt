package com.qiufang.bonbon.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.card.MaterialCardView
import com.qiufang.bonbon.R
import com.qiufang.bonbon.ui.music.Music
import com.qiufang.bonbon.ui.gallery.Gallery

class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    lateinit var galleryList : List<Gallery>
    private var mItemClickListener : OnItemClickListener? = null

    fun setData(list:List<Gallery>){
        galleryList = list
    }

    inner class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        val galleryImage : ImageView =  view.findViewById(R.id.img_gallery)
        val galleryName : TextView = view.findViewById(R.id.txt_gallery_name)
        val item : MaterialCardView = view.findViewById(R.id.gallery_item)
    }

    fun setListener(listener: OnItemClickListener){
        mItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gallery : Gallery =  galleryList[position]
        holder.galleryImage.load(gallery.url)
        holder.galleryName.text = gallery.name
        holder.item.setOnClickListener {
            mItemClickListener?.onItemClick(galleryList[position])
        }
    }

    override fun getItemCount() = galleryList.size

    interface OnItemClickListener{
        fun onItemClick(gallery: Gallery) {
        }
    }
}