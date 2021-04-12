package com.mutia.dsruput.view.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mutia.dsruput.R
import com.mutia.dsruput.adapter.BeritaAdapter
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.ResponseGetDatBerita
import kotlinx.android.synthetic.main.activity_berita.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeritaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berita)

        txtBackBerita.setOnClickListener {
            onBackPressed()
        }
        showBerita()
    }

    private fun showBerita() {
        val show = Network.service().getDataBerita()
        show.enqueue(object : Callback<ResponseGetDatBerita>{
            override fun onResponse(
                call: Call<ResponseGetDatBerita>,
                response: Response<ResponseGetDatBerita>
            ) {
                val data = response.body()?.data
                val adapter = BeritaAdapter(data)
                rvBerita.adapter = adapter
            }
            override fun onFailure(call: Call<ResponseGetDatBerita>, t: Throwable) {
                Toast.makeText(this@BeritaActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

