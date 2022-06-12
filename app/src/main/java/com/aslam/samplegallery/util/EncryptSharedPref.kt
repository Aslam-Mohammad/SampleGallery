package com.aslam.samplegallery.util

import android.content.SharedPreferences



class EncryptSharedPref(private val encryptedSharedPreferences: SharedPreferences) {


    fun saveString(key: String, value: String) {
        encryptedSharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    fun getString(key: String, defaultValue: String): String {
        return encryptedSharedPreferences.getString(key, defaultValue).toString()
    }

}