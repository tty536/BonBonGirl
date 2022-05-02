package com.qiufang.bonbon.ui.music

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qiufang.bonbon.R

class MusicAdapter(private val musicList :List<Music>) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    var mItemClickListener : OnItemClickListener? = null

    inner class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        val albumImage : ImageView =  view.findViewById(R.id.img_album)
        val albumName : TextView = view.findViewById(R.id.txt_album_name)
        val musicNumber : TextView =view.findViewById(R.id.number)
        val groupName :TextView = view.findViewById(R.id.txt_group)
        val musicState : ImageView = view.findViewById(R.id.music_state)
        val musicItem : LinearLayout = view.findViewById(R.id.music_item)
    }

    fun setListener(listener: OnItemClickListener){
        mItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_music,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val music : Music =  musicList[position]

//        holder.albumImage.load(music.imageUrl) {
//            transformations(CircleCropTransformation())
//        }
//        holder.albumImage.setOnClickListener { mItemClickListener?.onItemClick(music) }

        if (music.state) {
            holder.musicState.setBackgroundResource(R.mipmap.red_pause)
        }else{
            holder.musicState.setBackgroundResource(R.mipmap.red_play)
        }
        holder.albumName.text = music.name
        holder.musicNumber.text = (position+1).toString()
        holder.groupName.text = music.group
        holder.musicItem.setOnClickListener{
            mItemClickListener?.onItemClick(musicList,position) }
    }

    override fun getItemCount() = musicList.size

    interface OnItemClickListener{
        fun onItemClick(list: List<Music> ,position: Int) {

        }
    }
}