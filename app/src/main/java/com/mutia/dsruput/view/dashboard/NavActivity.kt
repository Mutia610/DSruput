package com.mutia.dsruput.view.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.mutia.dsruput.R
import kotlinx.android.synthetic.main.activity_nav.*

class NavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        val navController = Navigation.findNavController(this, R.id.home_nav_host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

    }
}