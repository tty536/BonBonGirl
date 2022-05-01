package com.qiufang.bonbon.ui.album

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.google.android.material.card.MaterialCardView
import com.qiufang.bonbon.R
import com.qiufang.bonbon.data.model.AlbumResult

class AlbumListAdapter(private val albumList: List<Album>?) : RecyclerView.Adapter<AlbumListAdapter.ViewHolder>() {

    var mItemClickListener : OnItemClickListener? = null

    inner class ViewHolder(view : View):RecyclerView.ViewHolder(view){
        val albumImage : ImageView =  view.findViewById(R.id.img_album)
        val albumName : TextView = view.findViewById(R.id.txt_album_name)
        val groupName :TextView = view.findViewById(R.id.txt_group_name)
        val item : MaterialCardView = view.findViewById(R.id.albumItem)
    }

    fun setListener(listener: OnItemClickListener){
        mItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album :Album =  albumList!![position]
        holder.albumImage.load(album.imageUrl)
        {
            placeholder(R.drawable.notfound)  //设置占位图

            //设置远角变换
//            transformations(RoundedCornersTransformation(8f))
            //圆形
//            transformations(CircleCropTransformation())
        }
        holder.item.setOnClickListener {
            mItemClickListener?.onItemClick(album)
        }
        holder.albumName.text = album.name
        holder.groupName.text = album.group
    }

    override fun getItemCount() = albumList!!.size

    interface OnItemClickListener{
        fun onItemClick(album: Album) {
            
        }
    }

}