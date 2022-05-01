package com.qiufang.bonbon.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class ToOtherActivityUtil {
    companion object{
        //    跳转到系统中的应用设置界面
        fun gotoAppSettingActivity(context : Activity){
            val intent : Intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package",context.packageName,null)
            intent.data = uri
            context.startActivity(intent)
        }
    }
}