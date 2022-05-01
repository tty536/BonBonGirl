package com.qiufang.bonbon.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.qiufang.bonbon.R
import com.qiufang.bonbon.databinding.ActivityLaunchBinding
import com.qiufang.bonbon.utils.Constants
import com.qiufang.bonbon.utils.PermissionUtil
import com.qiufang.bonbon.utils.StorageUtil

class LaunchActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLaunchBinding
    private var countdown = 3
    private lateinit var grantResults :IntArray

    private val mHandler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0){
                countdown--
                if(countdown == 0){
                    val intent = Intent(this@LaunchActivity,MainActivity::class.java)
                    startActivity(intent)
                    this@LaunchActivity.finish()
                }else{
                    binding.tvCount.text = countdown.toString()
                    sendEmptyMessageDelayed(0,1000)
                }
            }
        }
    }


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvCount.text = countdown.toString()
        window.statusBarColor = R.color.white
        PermissionUtil.requestPermission(this@LaunchActivity, Constants.mPermissions,object :
            PermissionUtil.Companion.OnPermissionCallBackListener{
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onGranted() {
                StorageUtil.createAPPFileDir()
//                倒计时进入APP
                mHandler.sendEmptyMessageDelayed(0,1000)
            }

            override fun onDenied(list: ArrayList<String>) {
                PermissionUtil.showDialog(this@LaunchActivity)
            }

        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        this.grantResults = grantResults
        PermissionUtil.onRequestPermissionResult(this,requestCode,Constants.mPermissions,grantResults)
    }

}