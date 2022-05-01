package com.qiufang.bonbon.ui.music

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qiufang.bonbon.utils.DBUtil
import com.qiufang.bonbon.utils.LogUtil
import java.lang.Exception
import kotlin.concurrent.thread

class MusicViewModel : ViewModel() {

    private val _musicList = MutableLiveData<List<Music>>()
    var musicList : LiveData<List<Music>> = _musicList

    fun getMusics(albumId : String,albumName : String){

        thread {
            val dbMusicList = ArrayList<Music>()

            val mHandler = object : Handler(Looper.getMainLooper()){
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    when(msg.what){
                    }
                }
            }
            try {
                DBUtil.init("bonbon")
                val sql = "select name,url from music where albumId = ?"
                val musics = DBUtil.queryMap(sql,albumId)
                for (i in musics.indices){
                    val music = Music(musics[i]["name"].toString(), musics[i]["url"].toString(),albumName)
                    LogUtil.d("getMusic",music.name+music.musicUrl)
                    dbMusicList.add(music)
                }
                if (dbMusicList.isNotEmpty()){
                    mHandler.post(Runnable {
                        _musicList.value = dbMusicList
                    })

                }
                DBUtil.closeDB()
            }catch (e:Exception){
                e.printStackTrace()
                DBUtil.closeDB()
            }
        }
    }
}