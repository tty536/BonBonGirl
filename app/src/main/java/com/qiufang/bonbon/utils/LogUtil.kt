package com.qiufang.bonbon.utils

import android.util.Log

object LogUtil {
    private const val VERBOSE = 1
    private const val DEBUG = 2
    private const val INFO = 3
    private const val WARN = 4
    private const val ERROR = 5

    private var level = INFO

    fun v (tag:String,msg:String){
        if (VERBOSE <= level)
            Log.v(tag,msg)
    }
    fun d (tag:String,msg:String){
        if (DEBUG <= level)
            Log.d(tag,msg)
    }
    fun i (tag:String,msg:String){
        if (INFO <= level)
            Log.i(tag,msg)
    }
    fun w (tag:String,msg:String){
        if (WARN<= level)
            Log.w(tag,msg)
    }
    fun e (tag:String,msg:String){
        if (ERROR<= level)
            Log.e(tag,msg)
    }
}