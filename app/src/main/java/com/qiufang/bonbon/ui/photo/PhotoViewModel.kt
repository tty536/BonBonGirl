package com.qiufang.bonbon.ui.photo

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qiufang.bonbon.utils.DBUtil
import com.qiufang.bonbon.utils.LogUtil
import java.lang.Exception
import kotlin.concurrent.thread

class PhotoViewModel : ViewModel() {
    private val _photoList = MutableLiveData<List<Photo>>()
    var photoList  = _photoList

    fun getPhoto(galleryId : String,galleryName : String){
        LogUtil.d("getPhoto","start")
        thread {
            val dbPhotoList = ArrayList<Photo>()
            val mHandler = object : Handler(Looper.getMainLooper()){
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    when(msg.what){
                    }
                }
            }
            try {
                DBUtil.init("bonbon")
                val sql = "select name,url from picture where galleryid = ?"
                val photos = DBUtil.queryMap(sql,galleryId)
                for (i in photos.indices){
                    val photo = Photo(photos[i]["name"].toString(), photos[i]["url"].toString(),galleryName)
                    LogUtil.d("getPhoto",photo.name+photo.url)
                    dbPhotoList.add(photo)
                }
                if (dbPhotoList.isNotEmpty()){
                    mHandler.post(Runnable {
                        _photoList.value = dbPhotoList
                    })

                }
                DBUtil.closeDB()
            }catch (e: Exception){
                LogUtil.d("getPhotoError","Error")
                e.printStackTrace()
                DBUtil.closeDB()
            }
        }
    }
}