package com.mutia.dsruput.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mutia.dsruput.R
import com.mutia.dsruput.preferences.SessionManager
import com.mutia.dsruput.preferences.PrefManager
import com.mutia.dsruput.view.dashboard.NavActivity

class SplashScreen : AppCompatActivity() {

    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
//        supportActionBar!!.hide()

//        prefManager = PrefManager(this)
//        var status_login = prefManager.getValueBoolean("statusLogin", false)

        val session = SessionManager(this)

        if (session.login ?: false){
            Handler().postDelayed(Runnable {

                startActivity(Intent(this, NavActivity::class.java))
                finish()

            },2000)
        }else{
            Handler().postDelayed(Runnable {

                startActivity(Intent(this, MainActivity::class.java))
                finish()

            },2000)
        }
    }
}