package com.mutia.dsruput.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.mutia.dsruput.R
import com.mutia.dsruput.preferences.PrefManager

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
//        supportActionBar!!.hide()

       // val session = PrefManager(this)

        Handler().postDelayed(Runnable {

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        },2000)
    }
}