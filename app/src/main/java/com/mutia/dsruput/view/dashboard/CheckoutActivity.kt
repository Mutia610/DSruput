package com.mutia.dsruput.view.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.mutia.dsruput.R
import com.mutia.dsruput.adapter.CheckoutAdapter
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.ResponseActionOrders
import com.mutia.dsruput.model.getDataKeranjang.ResponseGetDataKeranjang
import com.mutia.dsruput.preferences.PrefManager
import kotlinx.android.synthetic.main.activity_checkout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutActivity : AppCompatActivity() {

    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        prefManager = PrefManager(this)

        txtBackCheck.setOnClickListener {
            onBackPressed()
        }

        val alamat = prefManager.getValueString("AlamatUser")
        val totalHarga = intent.getStringExtra("totalHarga").toString()
        val id_costumer = prefManager.getValueInt("id")

        alamatUser.text = alamat.toString()
        totHarga.text = totalHarga
        hrgOngkir.text = "5000"
        var totalBayar = totalHarga.toInt() + 5000
        totPembayaran.text = totalBayar.toString()

        val namaUser = prefManager.getValueString("namaUser")
        NamaUser.text = namaUser.toString()

       showData()

        btnBuatPesanan.setOnClickListener {
            val metode = clickRadio()//radio.text.toString()

            simpanPesanan(
                id_costumer.toString(),
                namaUser.toString(),
                totalHarga,
                alamat.toString(),
                totalBayar.toString(),
                metode
            )
        }
    }

    private fun showData(){
        val id = prefManager.getValueInt("id")
        val tampil = Network.service().getDataKeranjang(id.toString())
        Log.d("usersss","id user  :"+ id)
        tampil.enqueue(object : Callback<ResponseGetDataKeranjang>{
            override fun onResponse(
                call: Call<ResponseGetDataKeranjang>,
                response: Response<ResponseGetDataKeranjang>
            ) {
                val data = response.body()?.data

                totBarang.text = data?.size.toString()

                val adapter = CheckoutAdapter(data)
                rvCheckout.adapter = adapter
            }

            override fun onFailure(call: Call<ResponseGetDataKeranjang>, t: Throwable) {
                Toast.makeText(this@CheckoutActivity, "Gagal", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun simpanPesanan(
        id_costumer: String?,
        nama_costumer: String?,
        total: String?,
        alamat_customer: String?,
        total_bayar: String?,
        metode: String?
    ) {

        val selectRg = radioGrup.checkedRadioButtonId
        if (selectRg == -1) {
            Toast.makeText(this, "Pilih Metode Pembayaran", Toast.LENGTH_SHORT).show()
        } else {
            val insert = Network.service()
                .insertOrders(id_costumer ?: "", nama_costumer ?: "",total ?: "", alamat_customer ?: "", total_bayar ?: "", metode ?: "")
            insert.enqueue(object : Callback<ResponseActionOrders> {
                override fun onResponse(
                    call: Call<ResponseActionOrders>,
                    response: Response<ResponseActionOrders>
                ) {
                    val code = response.body()?.code!!.toInt()
                    val idOrder = response.body()?.idOrder.toString()

                    if (response.isSuccessful){
                        if (code == 1) {
                            val intent = Intent(this@CheckoutActivity, KonfirmasiActivity::class.java)

                            val radio = clickRadio()

                            if (radio == "Transfer") {
                                intent.putExtra("Metode", 1)
                            } else if (radio== "COD") {
                                intent.putExtra("Metode", 2)
                            }

                            intent.putExtra("totalBayar", totPembayaran.text.toString())
                            intent.putExtra("idOrder", idOrder)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }else
                    {
                        Toast.makeText(applicationContext, "Respon Failed", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ResponseActionOrders>, t: Throwable) {
                    Toast.makeText(applicationContext, "Gagal Get Data", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    fun clickRadio(): String{
        val selectedRg = radioGrup.checkedRadioButtonId
        val radio: RadioButton = findViewById(selectedRg)

        return radio.text.toString()
    }
}