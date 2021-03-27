package com.mutia.dsruput.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.mutia.dsruput.R
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.action.ResponseAction
import com.mutia.dsruput.model.getMenu.DataMenu
import com.mutia.dsruput.preferences.PrefManager
import com.mutia.dsruput.view.dashboard.KeranjangActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_menu.*
import kotlinx.android.synthetic.main.activity_detail_menu.btnAdd
import kotlinx.android.synthetic.main.activity_detail_menu.btnMin
import kotlinx.android.synthetic.main.activity_detail_menu.txtJml
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMenu : AppCompatActivity() {

    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        txtBackDetail.setOnClickListener {
            onBackPressed()
        }

//        var jmlKrj = prefManager.getValueInt("jmlBagShop")
//
//        if (jmlKrj > 0){
//            jmlBeliDetail.visibility = View.VISIBLE
//            jmlBeliDetail.text = jmlKrj.toString()
//        }else{
//            jmlBeliDetail.visibility = View.GONE
//        }

        val getData = intent.getParcelableExtra<DataMenu>("data")
        val namaOutlet = intent.getStringExtra("outlet")
        val idOutlet = intent.getStringExtra("id_outlet")
        prefManager = PrefManager(this)

        if (getData != null){
            txtRasaMenuDetail.setText(getData.rasa)
            txtVarianMenuDetail.setText(getData.varian)
            txtHargaMenuDetail.setText(getData.harga)
            Picasso.get().load("http://192.168.43.84/dsruput/img/menu/" + getData.gambar).into(imgMenuDetail)
            txtNamaOutletDetail.setText(namaOutlet)
            txtTotalHargaDetail.setText(getData.harga)
        }

        if (getData?.status == "Tidak Tersedia"){
            txtStatusTersedia.visibility = View.GONE
            txtStatusTidakTersedia.visibility = View.VISIBLE
            txtJml.setText("0")

            btnOrderNow.setOnClickListener {
                Toast.makeText(this,"Sorry, Menu Sedang Kosong :(",Toast.LENGTH_SHORT).show()
            }
        }else
        {
            btnAdd.setOnClickListener {
                var jml = txtJml.text.toString().toInt() + 1
                txtJml.text = jml.toString()

                var harga = txtTotalHargaDetail.text.toString().toInt() + getData?.harga.toString().toInt()
                txtTotalHargaDetail.text = harga.toString()
            }

            btnMin.setOnClickListener {
                var jml = txtJml.text.toString().toInt()
                if (jml.equals(1)){
                    Toast.makeText(this, "Pesanan Tidak Bisa Dikurangi", Toast.LENGTH_SHORT).show()
                }else {
                    var jumlah = jml-1
                    txtJml.text = jumlah.toString()

                    val totHargaDetail = txtTotalHargaDetail.text.toString().toInt()
                    val hardaDb = getData?.harga!!.toInt()
                    var harga =  totHargaDetail + hardaDb

                    txtTotalHargaDetail.text = harga.toString()
                }
            }

            btnOrderNow.setOnClickListener {
                var toppingTambahan = onCheck()
                insertKeranjang(getData?.idMenu.toString(), idOutlet.toString(), prefManager.getValueInt("id").toString(), toppingTambahan, txtJml.text.toString(), txtTotalHargaDetail.text.toString())
                Log.d("detail", "idMenu : " + getData?.idMenu.toString() + "idOutlet : " + idOutlet.toString() + "idUser : " + prefManager.getValueInt("id").toString() + "topping : " + toppingTambahan + "jml : " + txtJml.text.toString() + " totaHarga : " + txtTotalHargaDetail.text.toString())
            }
        }
    }

    private fun insertKeranjang(id_menu: String, id_outlet: String, id_user: String, tambahan: String, jumlah: String, total_harga: String){
        val input = Network.service().insertKeranjang(id_menu ?: "", id_outlet ?: "", id_user ?: "",tambahan ?: "",jumlah ?: "", total_harga ?: "")
        input.enqueue(object : Callback<ResponseAction>{
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(this@DetailMenu, "Item Ditambahkan", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@DetailMenu, KeranjangActivity::class.java))
                finish()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(this@DetailMenu, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun onCheck(): String{
        var topping = " "

        if (chkBrownBubble.isChecked){
            if (topping != " "){
                topping = topping + " + " + chkBrownBubble.text.toString()
            }else{
                topping = chkBrownBubble.text.toString()
            }
        }

        if (chkRainbowJelly.isChecked){
            if (topping != " "){
                topping = topping + " + " + chkRainbowJelly.text.toString()
            }else{
                topping = chkRainbowJelly.text.toString()
            }
        }

        if (chkCoffeeJelly.isChecked){
            if (topping != " "){
                topping = topping + " + " + chkCoffeeJelly.text.toString()
            }else{
                topping = chkCoffeeJelly.text.toString()
            }

        }

        if (chkCreamCheese.isChecked){
            if (topping != " "){
                topping = topping + " + " + chkCreamCheese.text.toString()
            }else{
                topping = chkCreamCheese.text.toString()
            }
        }

        if (chkPoppingBoba.isChecked){
            if (topping != " "){
                topping = topping + " + " + chkPoppingBoba.text.toString()
            }else{
                topping = chkPoppingBoba.text.toString()
            }
        }

        if (chkRegal.isChecked){
            if (topping != " "){
                topping = topping + " + " + chkRegal.text.toString()
            }else{
                topping = chkRegal.text.toString()
            }
        }
        return topping
    }
}