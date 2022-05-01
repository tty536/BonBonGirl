package com.qiufang.bonbon.ui.album

class Album(val  id :String,val group :String, val name:String,val imageUrl : String,val musics : String){

    constructor(id: String,group: String,name: String,imageUrl: String)
            :this(id, group, name, imageUrl,"0")

    constructor(id: String,name: String,imageUrl: String)
            :this(id,"unknown",name,imageUrl,"0")
}