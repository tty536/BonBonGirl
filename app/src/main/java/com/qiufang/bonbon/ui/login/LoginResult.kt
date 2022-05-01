package com.qiufang.bonbon.ui.login

import com.qiufang.bonbon.ui.login.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult (
    val success: LoggedInUserView? = null,
    val error:Int? = null
)