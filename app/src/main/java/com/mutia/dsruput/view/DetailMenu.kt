package com.mutia.dsruput.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mutia.dsruput.R
import com.mutia.dsruput.model.getMenu.DataMenu
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_menu.*
import kotlinx.android.synthetic.main.activity_menu.*

class DetailMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        txtBackDetail.setOnClickListener {
            onBackPressed()
        }

        val getData = intent.getParcelableExtra<DataMenu>("data")
        val namaOutlet = intent.getStringExtra("outlet")

        if (getData != null){
            txtRasaMenuDetail.setText(getData.rasa)
            txtVarianMenuDetail.setText(getData.varian)
            txtHargaMenuDetail.setText(getData.harga)
            Picasso.get().load("http://192.168.43.84/dsruput/img/menu/" + getData.gambar).into(imgMenuDetail)
            txtNamaOutletDetail.setText(namaOutlet)
        }

        if (getData?.status == "Tidak Tersedia"){
            txtStatusTersedia.visibility = View.GONE
            txtStatusTidakTersedia.visibility = View.VISIBLE
            txtJml.setText("0")

            btnAddCart.setOnClickListener {
                Toast.makeText(this,"Sorry, Menu Sedang Kosong :(",Toast.LENGTH_SHORT).show()
            }
        }else
        {
            btnAdd.setOnClickListener {
                var jml = txtJml.text.toString().toInt() + 1
                txtJml.text = jml.toString()

                var harga = txtHargaMenuDetail.text.toString().toInt() + getData?.harga.toString().toInt()
                txtHargaMenuDetail.text = harga.toString()
            }

            btnMin.setOnClickListener {
                var jml = txtJml.text.toString().toInt()
                if (jml.equals(1)){
                    Toast.makeText(this, "Pesanan Tidak Bisa Dikurangi", Toast.LENGTH_SHORT).show()
                }else {
                    var jumlah = jml-1
                    txtJml.text = jumlah.toString()

                    var harga = txtHargaMenuDetail.text.toString().toInt() - getData?.harga.toString().toInt()
                    txtHargaMenuDetail.text = harga.toString()
                }
            }

            btnAddCart.setOnClickListener {
                Toast.makeText(this,"Terima Kasih :), Selesaikan proses Checkout",Toast.LENGTH_SHORT).show()
            }
        }
    }
}