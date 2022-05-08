package com.qiufang.bonbon.ui.album

class Album(
    val id:String,
    val group:String, val name:String,
    val imageUrl: String,
    val musics: String,
    val date: String?
){
    constructor(id: String,group: String,name: String,imageUrl: String,musics: String)
            :this(id, group, name, imageUrl,musics,null)

    constructor(id: String,group: String,name: String,imageUrl: String)
            :this(id, group, name, imageUrl,"0")

    constructor(id: String,name: String,imageUrl: String)
            :this(id,"unknown",name,imageUrl,"0")
}