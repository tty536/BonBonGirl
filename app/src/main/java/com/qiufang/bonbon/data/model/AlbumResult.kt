package com.qiufang.bonbon.data.model

import com.qiufang.bonbon.ui.album.Album
import com.qiufang.bonbon.ui.login.LoggedInUserView

data class AlbumResult(
    var success: ArrayList<Album>? = null,
    val error:Int? = null
)
