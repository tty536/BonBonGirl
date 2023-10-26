package com.qiufang.bonbon.ui.photo

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
import com.qiufang.bonbon.utils.LogUtil


class PhotoAdapter: RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    lateinit var photoList : List<Photo>
    private var mItemClickListener : OnItemClickListener? = null

    fun setData(list:List<Photo>){
        photoList = list
    }

    inner class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        val photoImage : ImageView =  view.findViewById(R.id.img_photo)
//        val bigImg:ImageView = view.findViewById(R.id.img_photo_big)
//        val layoutBig :LinearLayout = view.findViewById(R.id.layout_big)
//        val layoutSmall :LinearLayout  = view.findViewById(R.id.layout_small)
//        val photoName : TextView = view.findViewById(R.id.txt_photo)
//        val item : MaterialCardView = view.findViewById(R.id.item_photo)
    }

    fun setListener(listener: OnItemClickListener){
        mItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo : Photo =  photoList[position]
        holder.photoImage.load(photo.url){
            placeholder(R.drawable.notfound)
            transformations(RoundedCornersTransformation(10f))
        }
        holder.photoImage.setOnClickListener {
            mItemClickListener?.onItemClick(photo)
        }
//
//        holder.layoutSmall.setOnClickListener{
//            holder.layoutBig.visibility = View.GONE
//            holder.layoutSmall.visibility =  View.VISIBLE
//        }
//        LogUtil.d("photo","wide"+holder.photoImage.width+holder.photoImage.height)
//        holder.photoName.text = photo.name
//        holder.item.setOnClickListener {
//            mItemClickListener?.onItemClick(photoList[position])
//        }
    }

    override fun getItemCount() = photoList.size

    interface OnItemClickListener{
        fun onItemClick(photo: Photo) {
        }
    }

}