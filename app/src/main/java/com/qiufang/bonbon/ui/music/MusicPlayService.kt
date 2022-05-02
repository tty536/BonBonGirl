package com.qiufang.bonbon.ui.music

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.qiufang.bonbon.R
import com.qiufang.bonbon.utils.LogUtil
import java.lang.Exception

class MusicPlayService : Service() {

    private var position : Int = 0
    private lateinit var  musicList : List<Music>
    private var player : MediaPlayer = MediaPlayer()
    private val mBinder = PlayerServiceBinder()
    private var onStateChangeListener : OnMusicStateChangeListener? = null


    private lateinit var mRemoteView : RemoteViews
    private lateinit var receiver: MusicReceive
    private lateinit var notification: Notification
    private lateinit var manager: NotificationManager

    private val NOTIFY_CHANEL_ID = "001"
    private val MANAGE_ID = 100
    private  val BRE_ACTION_PLAY = "com.bonbon.play"
    private  val BRE_ACTION_PAUSE = "com.bonbon.pause"
    private  val BRE_ACTION_NEST = "com.bonbon.nest"
    private  val BRE_ACTION_AHEAD = "com.bonbon.ahead"

    companion object{
        private val mService = MusicPlayService()
    }



    class PlayerServiceBinder :Binder(){
        fun getService(): MusicPlayService {
            return mService
        }
    }

    override fun onCreate() {
        super.onCreate()
        initMusicReceiver()
        createRemoteView()
        initNotification()
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

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
    class MusicReceive : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val action = p1?.action
            if (action != null) {
                MusicPlayService().notifyControlUI(action)
            }

        }
    }

    private fun initMusicReceiver(){
        receiver = MusicReceive()
        val intentFilter = IntentFilter()
        intentFilter.addAction(BRE_ACTION_AHEAD)
        intentFilter.addAction(BRE_ACTION_NEST)
        intentFilter.addAction(BRE_ACTION_PAUSE)
        intentFilter.addAction(BRE_ACTION_PLAY)
        registerReceiver(receiver,intentFilter)
    }

    private fun createRemoteView(){
        mRemoteView = RemoteViews(packageName,R.layout.notify_music)

        val intentAhead :PendingIntent = PendingIntent.getBroadcast(
            this,1,Intent(BRE_ACTION_AHEAD),PendingIntent.FLAG_UPDATE_CURRENT)
        mRemoteView.setOnClickPendingIntent(R.id.img_ahead,intentAhead)

        val intentPlay :PendingIntent = PendingIntent.getBroadcast(
            this,1,Intent(BRE_ACTION_PLAY),PendingIntent.FLAG_UPDATE_CURRENT)
        mRemoteView.setOnClickPendingIntent(R.id.img_play,intentAhead)

        val intentNext :PendingIntent = PendingIntent.getBroadcast(
            this,1,Intent(BRE_ACTION_NEST),PendingIntent.FLAG_UPDATE_CURRENT)
        mRemoteView.setOnClickPendingIntent(R.id.img_next,intentAhead)

        val intentPause :PendingIntent = PendingIntent.getBroadcast(
            this,1,Intent(BRE_ACTION_PAUSE),PendingIntent.FLAG_UPDATE_CURRENT)
        mRemoteView.setOnClickPendingIntent(R.id.img_pause,intentAhead)

    }

    private fun initNotification(){
        manager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        val  builder : NotificationCompat.Builder = NotificationCompat.Builder(
                                    this,NOTIFY_CHANEL_ID)

        builder.setSmallIcon(R.drawable.icon_logo)
            .setContent(mRemoteView)
            .setAutoCancel(false)
//            .setOngoing(true)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
        notification = builder.build()
        manager.notify(MANAGE_ID,notification)
    }

    private fun updateNotification(position: Int){

        if (player.isPlaying){
            mRemoteView.setViewVisibility(R.id.img_play,View.GONE)
            mRemoteView.setViewVisibility(R.id.img_pause,View.VISIBLE)
        }else{
            mRemoteView.setViewVisibility(R.id.img_play,View.GONE)
            mRemoteView.setViewVisibility(R.id.img_pause,View.VISIBLE)
        }

        mRemoteView.setTextViewText(R.id.music_title,musicList[position].name)
        mRemoteView.setTextViewText(R.id.group_name,musicList[position].group)

        manager.notify(MANAGE_ID,notification)
    }

    private fun notifyControlUI(action: String){
//        when (action){
//            BRE_ACTION_PLAY ->
//        }
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
                    updateNotification(position)

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
                updateNotification(position)
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


    interface OnMusicStateChangeListener{
        fun onStateChange(position: Int){
        }
    }

    fun setOnMusicStateChangeListener(listener : OnMusicStateChangeListener){
        this.onStateChangeListener = listener
    }

    private fun notifyActivityUIChange(){
        if (this.onStateChangeListener != null){
            this.onStateChangeListener!!.onStateChange(this.position)
        }
    }


}