package com.qiufang.bonbon.utils

import com.qiufang.bonbon.R
import com.qiufang.bonbon.ui.album.Album
import com.qiufang.bonbon.ui.music.Music

class Constants {
    companion object{
        lateinit var APP_FILE_DIR :String
        lateinit var userName : String
        lateinit var userHeadUrl :String
        val mPermissions =  listOf(
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        fun getEmptyList():List<Album>{
            val emptyAlbumList = mutableListOf<Album>()
            val i = 1
            while (i<10){
                emptyAlbumList.add(Album("0","空专辑", "https://bonbon-music.oss-cn-hangzhou.aliyuncs.com/picture/%E4%B8%8D%E5%AD%98%E5%9C%A8%281%29.png"))
            }
            return emptyAlbumList
        }


    }

    class MusicList {

        private lateinit var musicList: List<Music>

        fun setMusics(musics:List<Music>){
                musicList =  musics
        }

        fun getMusics():List<Music>{
            return musicList.ifEmpty {
                getEmptyMusicList()
            }
        }

        private fun getEmptyMusicList():List<Music>{
            val emptyMusic  : ArrayList<Music> = ArrayList()
            for (i in 0..2){
                emptyMusic.add(Music("null","null","null"))
            }
            return emptyMusic
        }

    }

}