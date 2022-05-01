package com.qiufang.bonbon.ui.music

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.qiufang.bonbon.utils.Constants
import com.qiufang.bonbon.utils.LogUtil
import java.lang.Exception

class MusicPlayService : Service() {

    private var position : Int = 0
    private lateinit var  musicList : List<Music>
    private var player : MediaPlayer = MediaPlayer()
    private val mBinder = PlayerServiceBinder()
    private var onStateChangeListener : OnMusicStateChangeListener? = null

    interface OnMusicStateChangeListener{
        fun onStateChange(position: Int){
        }
    }

    fun setOnMusicStateChangeListener(listener : OnMusicStateChangeListener){
        this.onStateChangeListener = listener
    }

    fun notifyActivityUIChange(){
        if (this.onStateChangeListener != null){
            this.onStateChangeListener!!.onStateChange(this.position)
        }
    }
    companion object{
        private val mService = MusicPlayService()
    }



    class PlayerServiceBinder :Binder(){
        fun getService(): MusicPlayService {
            return mService
        }
    }


    override fun onBind(intent: Intent): IBinder {
       return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
//        if (player is MediaPlayer){
//            player.reset()
//        }
        return super.onUnbind(intent)
    }

    fun startPlay(list : List<Music>, position : Int){
//        if (null != MusicManager.Musics.getMusicData()){
//            musicList = MusicManager.Musics.getMusicData()!!
//        }else{
//            LogUtil.e("MusicService","not work MusicList")
//            return
//        }
        musicList = list

        if ( player == null){
            player = MediaPlayer()
        }

        try {
            if (player.isPlaying) {
                player.stop()
                list[this.position].state = false
                notifyActivityUIChange()

                if (this.position != position){
                    this.position = position

                    val music =  list[this.position]
                    player.reset()
                    player.setDataSource(music.musicUrl)
                    player.prepare()
                    player.start()
                    list[this.position].state = true
                    notifyActivityUIChange()


                }
            }else{
                this.position = position

                val music =  list[this.position]
                player.reset()
                player.setDataSource(music.musicUrl)
                player.prepare()
                player.start()
                list[this.position].state = true
                notifyActivityUIChange()
            }
        }catch (e:Exception){
            e.printStackTrace()
            LogUtil.e("MusicPlayer_Wrong",e.toString())
        }
    }

    private fun playMusic(position: Int){
        val music =  musicList[this.position]
        player.reset()
        player.setDataSource(music.musicUrl)
        player.prepare()
        player.start()
        music.state = true
        notifyActivityUIChange()
    }


}