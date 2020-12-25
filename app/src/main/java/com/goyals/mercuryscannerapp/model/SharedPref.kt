package com.goyals.mercuryscannerapp.model

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
  companion object {
    private const val PREF_NAME = "mercury"
    private const val IS_LOGGED_IN = "is_logged_in"
    private const val USER_ID = "user_id"
    private const val USER_NAME = "user_name"
  }

  private val sharedPref: SharedPreferences =
    context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

  fun isLoggedIn(): Boolean = sharedPref.getBoolean(IS_LOGGED_IN, false)

  fun setLoggedIn(boolean: Boolean) {
    val editor = sharedPref.edit()
    editor.putBoolean(IS_LOGGED_IN, boolean)
    editor.apply()
    editor.commit()
  }

  fun getUserId(): Int = sharedPref.getInt(USER_ID, 0)

  fun setUserId(id: Int) {
    val editor = sharedPref.edit()
    editor.putInt(USER_ID, id)
    editor.apply()
    editor.commit()
  }

  fun getUserName(): String? = sharedPref.getString(USER_NAME, "")

  fun setUserName(name: String) {
    val editor = sharedPref.edit()
    editor.putString(USER_NAME, name)
    editor.apply()
    editor.commit()
  }
}