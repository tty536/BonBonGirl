package com.qiufang.bonbon.ui.login

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qiufang.bonbon.R
import com.qiufang.bonbon.data.model.LoginRepository
import com.qiufang.bonbon.utils.DBUtil
import java.lang.Exception
import kotlin.concurrent.thread


class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(userID: String, password: String) {
        // can be launched in a separate asynchronous job
        val mHandler = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when(msg.what){
                    0 -> _loginResult.value = LoginResult(error = R.string.Unknown)
                    1 -> _loginResult.value = LoginResult(success = LoggedInUserView(displayName = msg.obj as String))
                    2 -> _loginResult.value = LoginResult(error = R.string.NotAccount)
                    3 -> _loginResult.value = LoginResult(error = R.string.login_failed)
                    4 -> _loginResult.value = LoginResult(error = R.string.account_warning)
                }
            }
        }
        try {
            val id = userID.toInt()
            thread {
                try {
                    DBUtil.init("bonbon")
                    if (DBUtil.checkAccount(id)){
                        if (DBUtil.login(id,password)){
                            val message = Message()
                            val sql  = "select name from users where id=? and password=?"
                            val name = DBUtil.queryMap(sql,id,password)
                            if(name  != null){
                                mHandler.post(Runnable {
                                    _loginResult.value = LoginResult(success = LoggedInUserView(displayName = name[0].values.toString()))
                                })
                            }else{
                                mHandler.post(Runnable {
                                    _loginResult.value = LoginResult(success = LoggedInUserView(displayName = "Unknown"))
                                })
                            }
                        }else{
                            mHandler.sendEmptyMessage(4)
                        }
                    }else{
                        mHandler.sendEmptyMessage(2)
                    }
                    DBUtil.closeDB()
                }catch (e:Exception){
                    mHandler.sendEmptyMessage(3)
                    DBUtil.closeDB()
                }
            }
        }catch (e:Exception){
            mHandler.sendEmptyMessage(3)
            DBUtil.closeDB()
        }

//        val result = loginRepository.login(username, password)
//
//        if (result is Result.Success) {
//            _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
//        } else {
//            _loginResult.value = LoginResult(error = R.string.login_failed)
//        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.matches(Regex("[0-9]{5,11}"))
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}