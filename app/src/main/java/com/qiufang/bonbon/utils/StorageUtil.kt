package com.qiufang.bonbon.utils

import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import java.io.File

class StorageUtil private constructor(){
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

        private fun isHasExtraStorage(): Boolean {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
        }

        @RequiresApi(Build.VERSION_CODES.R)
        fun createAPPFileDir() : File? {
            if (isHasExtraStorage()){
                val dir  = Environment.getDataDirectory()
                val fileDir  = File(dir,"sounds")
                if (!fileDir.exists()){
                    fileDir.mkdir()
                }
                Constants.APP_FILE_DIR = fileDir.absolutePath
                return fileDir
            }
            return null
        }

        @RequiresApi(Build.VERSION_CODES.R)
        fun createBranchFile(dir:String):File{
            val appFileDir = createAPPFileDir()
            val newDir = File(appFileDir,dir)
            if(!newDir.exists()){
                newDir.mkdir()
            }
            return newDir
        }
    }
}