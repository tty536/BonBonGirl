package com.qiufang.bonbon.ui.music

import android.annotation.SuppressLint
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.qiufang.bonbon.R
import com.qiufang.bonbon.databinding.ActivityMusicBinding
import com.qiufang.bonbon.utils.Constants
import com.qiufang.bonbon.utils.LogUtil

class MusicActivity : AppCompatActivity() {

    private lateinit var musicViewModel: MusicViewModel
    private lateinit var binding: ActivityMusicBinding

    private lateinit var albumId : String
    private lateinit var albumGroup : String
    private lateinit var albumName : String
    private lateinit var albumCover : String
    private lateinit var albumDate : String
    lateinit var adapter : MusicAdapter
    private val TAG : String = "MusicActivity"

//    private val musicList :List<Music> = musicViewModel.initMusics()
    private lateinit var mBinder : MusicPlayService.PlayerServiceBinder
    private lateinit var mService: MusicPlayService


    private val serviceConnection  =object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {

            mBinder = p1 as MusicPlayService.PlayerServiceBinder
            mService = mBinder.getService()

            LogUtil.d("binder Service",mService.toString())
            mService.setOnMusicStateChangeListener(object :MusicPlayService.OnMusicStateChangeListener{
                @SuppressLint("NotifyDataSetChanged")
                override fun onStateChange(position: Int) {
                    super.onStateChange(position)
                    adapter.notifyDataSetChanged()
                }
            })
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            TODO("Not yet implemented")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        albumId = intent.getStringExtra("album_id").toString()
        albumGroup = intent.getStringExtra("album_groupName").toString()
        albumName = intent.getStringExtra("album_name").toString()
        albumCover = intent.getStringExtra("album_cover").toString()
        albumDate = intent.getStringExtra("album_date").toString()

        musicViewModel = ViewModelProvider(this).get(MusicViewModel::class.java)
        val musicList = binding.albumMusic
        val imgCover = binding.albumCover
        val txtAlbumName = binding.albumName
        val txtGroupName = binding.albumGroup
        val txtDate = binding.txtDate

        imgCover.load(albumCover)
        txtAlbumName.text = albumName
        txtGroupName.text = albumGroup
        txtDate.text  = albumDate
        val layoutManager = LinearLayoutManager(this)
        musicList.layoutManager = layoutManager
        musicList.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

        val intent = Intent(this@MusicActivity,MusicPlayService::class.java)
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)

        musicViewModel.getMusics(albumId,albumGroup)
        musicViewModel.musicList.observe(this, Observer {
            if (it.isNotEmpty()){

                MusicManager.Musics.setMusicData(it)
                val adapter = MusicAdapter(it)
                adapter.setListener(object :MusicAdapter.OnItemClickListener{
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onItemClick(list: List<Music>,position: Int) {
                        super.onItemClick(list,position)
                        mService.startPlay(list,position)
                        LogUtil.d("Service",mService.toString())
//                        MusicManager.Musics.musics[position].state  = !MusicManager.Musics.musics[position].state
//                        adapter.notifyDataSetChanged()
                    }
                })
                this.adapter = adapter
                MusicManager().setMusicAdapter(adapter)

                musicList.adapter = adapter
                LogUtil.d("MusicPlayer_Wrong","MusicActivity set Adapter")

            }
        })

    }

    override fun onStop() {
        LogUtil.d(TAG,"stop Activity")
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(TAG,"onDestroy Activity")
//        unbindService(serviceConnection)
//        val intent = Intent(this@MusicActivity,MusicPlayService::class.java)
//        stopService(intent)
    }

}