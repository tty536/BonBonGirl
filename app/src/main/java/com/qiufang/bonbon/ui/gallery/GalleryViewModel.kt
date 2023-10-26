package com.qiufang.bonbon.ui.gallery

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qiufang.bonbon.utils.Constants
import com.qiufang.bonbon.utils.DBUtil
import com.qiufang.bonbon.utils.LogUtil
import java.lang.Exception
import kotlin.concurrent.thread

class GalleryViewModel : ViewModel() {

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is gallery Fragment"
//    }
//    val text: LiveData<String> = _text

    private val _listGallery =   MutableLiveData<List<Gallery>>()
    var galleryList :LiveData<List<Gallery>> = _listGallery

    fun getGallery() {
        val mHandler = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when(msg.what){
                }
            }
        }
        try {
            thread {
                try {
                    val dbGalleryList = ArrayList<Gallery>()
                    DBUtil.init("bonbon")
                    val sql = "select  id,name,url from gallery"
                    val gallery = DBUtil.queryMap(sql)
                    for (i in gallery.indices){
                        val item = Gallery( gallery[i]["id"].toString(),gallery[i]["name"].toString(),gallery[i]["url"].toString())
                        dbGalleryList.add(item)
                    }
                    if(dbGalleryList.isNotEmpty()){
                        mHandler.post {
                            _listGallery.value =  dbGalleryList
                        }
                    }else{
                        mHandler.post {
                            _listGallery.value =  Constants.getEmptyGalleryList()
                        }
                    }

                    LogUtil.d("getGallery",gallery.toString())
                    DBUtil.closeDB()

                }catch (e: Exception){
                    LogUtil.e("getGallery",e.toString())
                    DBUtil.closeDB()
                }
            }
        }catch (e: Exception){
            LogUtil.e("getGallery",e.toString())
            DBUtil.closeDB()
        }
    }
}