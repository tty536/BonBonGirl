package com.qiufang.bonbon.ui.music

import android.net.Uri

class Music(val name:String, val musicUrl:String, val group:String, var state:Boolean){

    constructor(name: String,musicUrl: String,group: String):this(name, musicUrl, group, false){

    }
}