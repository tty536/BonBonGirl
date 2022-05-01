package com.qiufang.bonbon.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.qiufang.bonbon.R

//class PermissionUtil {
//}
class PermissionUtil private constructor(){
    /*kotlin单例类的创建*/
//    companion object{
//        private var instance: PermissionUtil? = null
//        get() {
//            if (field  == null){
//                field  = PermissionUtil()
//            }
//            return instance
//        }
//
//        @Synchronized
//        fun get():PermissionUtil{
//            return instance!!;
//        }
//    }
    /*单例类的双重校验锁*/
    companion object{
//        private val instance:PermissionUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
//            PermissionUtil()
//        }
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

    private const val requestCode:Int = 99
    private var mListener : OnPermissionCallBackListener? = null

    interface OnPermissionCallBackListener{
        fun onGranted()
        fun onDenied(list:ArrayList<String>)
    }
    fun  requestPermission(context : Activity,permissions : List<String>,listener: OnPermissionCallBackListener){
        mListener = listener
        /*判断版本Android 6 以上*/
        if (Build.VERSION.SDK_INT >= 23 ){
            val permissionList = mutableListOf<String>()
            for (permission in permissions){
                if(ContextCompat.checkSelfPermission(context,permission)
                    !=PackageManager.PERMISSION_GRANTED){
                    permissionList.add(permission)
                }
            }
            if(permissionList.size > 0)
            {
                val permission: MutableList<String> = permissionList
                ActivityCompat.requestPermissions(context, permission.toTypedArray(),requestCode)
            }else{
                mListener?.onGranted()
            }
        }
    }

    fun onRequestPermissionResult(activity: Activity,requestCode: Int,
                                  permissions: List<String>, grantResults: IntArray){
        if (requestCode == this.requestCode ){
            val deniedPermission = mutableListOf<String>()
            val size : Int = grantResults.size
            if (size != 0 ){
                for (i in 0 until size){
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        deniedPermission.add(permissions[i])
                    }
                }
                if (deniedPermission.size == 0){
                    mListener?.onGranted()
                }else{
                    deniedPermission.let { mListener?.onDenied(it as ArrayList<String>) }
                }
            }else{
                mListener?.onGranted()
            }

        }
    }

    fun showDialog(context: Activity){
        DialogUtil.showDialog(context,  context.getString(R.string.tips),
            context.getString(R.string.permission_not_pass),
            context.getString(R.string.cancel),object : DialogUtil.OnNegativeClickListener {
                override fun onClick() {
                    context.finish()
                }
            } ,
            context.getString(R.string.OK),object : DialogUtil.OnPositiveClickListener {
                override fun onClick() {
                    ToOtherActivityUtil.gotoAppSettingActivity(context)
                    context.finish()
                }
            })

    }
    }
}