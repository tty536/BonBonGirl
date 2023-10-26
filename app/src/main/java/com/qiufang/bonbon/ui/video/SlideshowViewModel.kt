package com.qiufang.bonbon.ui.video

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qiufang.bonbon.ui.gallery.Gallery
import com.qiufang.bonbon.ui.photo.Photo
import com.qiufang.bonbon.utils.DBUtil
import com.qiufang.bonbon.utils.LogUtil
import java.lang.Exception
import kotlin.concurrent.thread

class SlideshowViewModel : ViewModel() {
    private val _videoList = MutableLiveData<List<Video>>()
    val videoList :LiveData<List<Video>> = _videoList

    fun getVideo(){
        thread {
            val dbVideoList = ArrayList<Video>()
            val mHandler = object : Handler(Looper.getMainLooper()){
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                }
            }

            try {
                DBUtil.init("bonbon")
                val sql = "select title,cover,url,introduce from video"
                val videos = DBUtil.queryMap(sql)
                for (i in videos.indices){
                    val video = Video(videos[i]["title"].toString(), videos[i]["cover"].toString(),videos[i]["url"].toString(),videos[i]["introduce"].toString(),)
                    LogUtil.d("getVideo",video.title+video.url)

                    dbVideoList.add(video)

                }
                if (dbVideoList.isNotEmpty()){
                    mHandler.post(Runnable {
                        _videoList.value = dbVideoList
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