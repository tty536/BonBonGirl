package com.qiufang.bonbon.ui.album

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qiufang.bonbon.data.model.AlbumResult
import com.qiufang.bonbon.utils.Constants
import com.qiufang.bonbon.utils.DBUtil
import com.qiufang.bonbon.utils.LogUtil
import java.lang.Exception
import kotlin.concurrent.thread

class AlbumViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _listAlbum =   MutableLiveData<List<Album>>()
    var albumList :LiveData<List<Album>> = _listAlbum

    fun getAlbum() {
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
                    val dbAlbumList = ArrayList<Album>()
                    DBUtil.init("bonbon")
                    val sql = "select  id,groupname,name,url,musics  from album"
                    val album = DBUtil.queryMap(sql)
                    for (i in album.indices){
                         val dbAlbum = Album( album[i]["id"].toString(),album[i]["groupname"].toString(),album[i]["name"].toString(),
                             album[i]["url"].toString(),album[i]["musics"].toString())
                        dbAlbumList.add(dbAlbum)
                    }
                    if(dbAlbumList.isNotEmpty()){
                        mHandler.post {
                            _listAlbum.value =  dbAlbumList
                        }
                    }else{
                        mHandler.post {
                            _listAlbum.value =  Constants.getEmptyList()
                        }
                    }

                    LogUtil.d("getAlbum",album.toString())
                    DBUtil.closeDB()

                }catch (e:Exception){
                    LogUtil.e("getAlbumFromDB",e.toString())
                    DBUtil.closeDB()
                }
            }
        }catch (e:Exception){
            LogUtil.e("getAlbumFromDB",e.toString())
            DBUtil.closeDB()
        }
    }

}