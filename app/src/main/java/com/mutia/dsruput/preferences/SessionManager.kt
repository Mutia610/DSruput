package com.mutia.dsruput.preferences

import android.content.Context
import android.content.SharedPreferences

class SessionManager(var context: Context) {

    var pref : SharedPreferences?= null
    var editor : SharedPreferences.Editor?=null
    var PREF_NAME = "LOGIN"

    var ISLOGIN="isLogin"
    var NAMA = "username"
    var EMAIL = "email"

    init{
        pref = context.getSharedPreferences(PREF_NAME,0)
        editor = pref?.edit()
    }

    var login: Boolean?

    get() = pref?.getBoolean(ISLOGIN,false)
    set(login){
        editor?.putBoolean(ISLOGIN,true)
        editor?.commit()
    }

    var nama :String?
    get() = pref?.getString(NAMA,"")
    set(nama){
        editor?.putString(NAMA,nama)
        editor?.commit()
    }

    fun logout(){
        editor?.clear()
        editor?.commit()
    }
}