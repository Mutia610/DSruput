package com.mutia.dsruput.view.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.mutia.dsruput.R
import com.mutia.dsruput.adapter.DetailRiwayatAdapter
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.ResponseDetailOrder
import com.mutia.dsruput.model.ResponseDetailRiwayat
import kotlinx.android.synthetic.main.activity_detail_riwayat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRiwayatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_riwayat)

        txtBackDeR.setOnClickListener {
            onBackPressed()
        }

        totBarangDeR.text = intent.getStringExtra("total_barang")

        showDetailRiwayat()
        getData()

    }

    private fun getData() {
        val id_order = intent.getStringExtra("id_order").toString()

        val getData = Network.service().getDetailOrder(id_order)
        getData.enqueue(object : Callback<ResponseDetailOrder>{
            override fun onResponse(
                call: Call<ResponseDetailOrder>,
                response: Response<ResponseDetailOrder>
            ) {
                NamaUserDeR.text = response.body()?.username
                alamatUserDeR.text = response.body()?.alamat
                statusDeR.text = response.body()?.status
                totHargaDeR.text = response.body()?.total_harga
                hrgOngkirDeR.text = response.body()?.ongkir
                totPembayaranDeR.text = response.body()?.total_bayar
            }

            override fun onFailure(call: Call<ResponseDetailOrder>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun showDetailRiwayat() {
        val id_order = intent.getStringExtra("id_order").toString()

        val showDetailRiwayat = Network.service().getOrderDetail(id_order)
        showDetailRiwayat.enqueue(object : Callback<ResponseDetailRiwayat>{
            override fun onResponse(
                call: Call<ResponseDetailRiwayat>,
                response: Response<ResponseDetailRiwayat>
            ) {
                val item = response.body()?.data

                val adapter = DetailRiwayatAdapter(item)
                rvDetailRiwayat.adapter = adapter
            }

            override fun onFailure(call: Call<ResponseDetailRiwayat>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}