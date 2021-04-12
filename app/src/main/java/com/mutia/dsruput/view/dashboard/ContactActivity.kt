package com.mutia.dsruput.view.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mutia.dsruput.R
import kotlinx.android.synthetic.main.activity_contact.*

class ContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        txtBackContact.setOnClickListener {
            onBackPressed()
        }
    }
}