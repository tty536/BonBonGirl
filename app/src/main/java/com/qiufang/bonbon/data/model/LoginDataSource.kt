package com.qiufang.bonbon.data.model

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.qiufang.bonbon.ui.login.LoginActivity
import com.qiufang.bonbon.utils.DBUtil
import com.qiufang.bonbon.utils.DBUtil.Companion.login
import java.io.IOException
import java.lang.Exception
import java.sql.Connection
import java.util.*
import kotlin.concurrent.thread

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {

        return try {

            val mHandler = object : Handler(Looper.getMainLooper()){
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    if (msg.what == 1){

                    }
                }
            }

            thread {
                Looper.prepare()
                try {
                    DBUtil.init("bonbon")
                    if (DBUtil.login(username.toInt(),password)){

                    }
                    DBUtil.closeDB()

                }catch (e:Exception){
                    mHandler.sendEmptyMessage(2)
                }
            }
            val fakeUser = LoggedInUser(UUID.randomUUID().toString(), "Jane Doe")
            Result.Success(fakeUser)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}