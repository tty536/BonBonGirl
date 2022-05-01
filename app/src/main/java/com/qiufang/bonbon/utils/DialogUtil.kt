package com.qiufang.bonbon.utils

import android.app.Activity
import android.app.Dialog
import androidx.appcompat.app.AlertDialog
import org.xml.sax.helpers.DefaultHandler
import org.xmlpull.v1.XmlPullParser

open class DialogUtil {
    companion object{
            private var instance: DialogUtil? = null
                get() {
                    if (field  == null){
                        field  = DialogUtil()
                    }
                    return instance
                }

            @Synchronized
            fun get():DialogUtil{
                return instance!!;
            }

        fun showDialog(
            context: Activity, title: String, msg:String,
            negativeButton:String, negativeListener:OnNegativeClickListener,
            positiveButton:String, positiveClickListener: OnPositiveClickListener){

            val build = AlertDialog.Builder(context)
            build.setTitle(title)
                .setMessage(msg)
//        build.setNegativeButton(negativeButton,object :DialogInterface.OnClickListener{
//            override fun onClick(p0: DialogInterface?, p1: Int) {
//                TODO("Not yet implemented")
//            }
//
//        })
            build.setNegativeButton(negativeButton){
                    p0, p1 ->negativeListener.onClick()  }
            build.setPositiveButton(positiveButton){
                    p0,p1 ->positiveClickListener.onClick()
            }
            build.setCancelable(false)
            build.create().show()
        }
    }

    interface OnNegativeClickListener{
        fun onClick()
    }
    interface OnPositiveClickListener{
        fun onClick()
    }


}
