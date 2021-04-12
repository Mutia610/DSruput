package com.mutia.dsruput.preferences

import android.content.Context
import android.content.SharedPreferences
import com.mutia.dsruput.adapter.OutletAdapater

class PrefManager(var c:Context) {

    private val PREFS_NAME = "lokasi"
    val sharedPref: SharedPreferences = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var ISLOGIN="isLogin"
    var login: Boolean?

    get() = sharedPref?.getBoolean(ISLOGIN,false)

    set(login){
        val editor: SharedPreferences.Editor = sharedPref.edit()
         editor?.putBoolean(ISLOGIN,true)
         editor?.commit()
    }

    fun save(KEY_NAME: String, text: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor!!.commit()
    }

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(KEY_NAME, value)
        editor.commit()
    }

    fun save(KEY_NAME: String, status: Boolean) {

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, status!!)
        editor.commit()
    }

    fun save(KEY_NAME: String, latitude: Float) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putFloat(KEY_NAME, latitude)
        editor.commit()
    }

    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getValueInt(KEY_NAME: String): Int {
        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun getValueFloat(KEY_NAME: String): Float {
        return sharedPref.getFloat(KEY_NAME, 0F)
    }

    fun getValueBoolean(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue)
    }

    fun logout() {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor.clear()
        editor.commit()
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.commit()
    }





//    var pref : SharedPreferences? = null
//    var editor : SharedPreferences.Editor? = null
//    var PREF_NAME = "LOGINUSER"
//
//    var ISILOGIN = "isilogin"
//    var USERNMAE = "username"
//    var EMAIL = "email"
//
//    init {
//        pref = c.getSharedPreferences(PREF_NAME, 0)
//        editor = pref?.edit()
//    }
//
//    var login : Boolean?
//    get() = pref?.getBoolean(ISILOGIN, false)
//    set(login){
//        editor?.putBoolean(ISILOGIN, true)
//        editor?.commit()
//    }
//
//    var username : String?
//    get() = pref?.getString(USERNMAE, "")
//    set(username){
//        editor?.putString(USERNMAE, username)
//    }
//
//    var email : String?
//    get() = pref?.getString(EMAIL, "")
//    set(email){
//        editor?.putString(EMAIL, email)
//    }
}