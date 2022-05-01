package com.qiufang.bonbon.ui.music

import android.annotation.SuppressLint

class MusicManager {

    private  var mMusicAdapter: MusicAdapter? = null



    fun setMusicAdapter(musicAdapter: MusicAdapter){
        mMusicAdapter = musicAdapter
    }

    fun getMusicAdapter(): MusicAdapter? {
        return mMusicAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyChange(){
        mMusicAdapter?.notifyDataSetChanged()
    }
    object Musics{
    lateinit var musics : List<Music>

        fun setMusicData(musicList :List<Music>){
            musics = musicList
        }

        fun getMusicData(): List<Music>? {
            return musics.ifEmpty {
                null
            }
        }
    }
}