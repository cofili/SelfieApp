package com.example.selfieapp.helpers

import android.content.Context
import com.example.selfieapp.models.User

class SessionManager(var mContext: Context) {


    private val FILE_NAME = "selfieapp_pref"
    private val KEY_ID = "id"
    private val KEY_EMAIL = "email"
    private val KEY_PASSWORD = "password"
    private val KEY_NAME = "name"
    private val KEY_MOBILE = "mobile"
    private val KEY_IS_LOGGED_IN = "isLoggedIn"

    var sharedPreferences = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }


}
