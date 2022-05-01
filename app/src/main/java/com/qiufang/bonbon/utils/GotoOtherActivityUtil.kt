package com.qiufang.bonbon.utils

import android.app.Activity
import android.content.Context
import android.content.Intent


object GotoOtherActivityUtil {
    fun gotoOtherActivity(context: Context,activity: Activity){
        val intent = Intent(context, activity.javaClass)
        context.startActivity(intent)
    }
}