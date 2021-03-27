package com.mutia.dsruput.view.dashboard

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mutia.dsruput.R
import com.mutia.dsruput.adapter.KeranjangAdapter
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.action.ResponseAction
import com.mutia.dsruput.model.getDataKeranjang.DataItemKeranjang
import com.mutia.dsruput.model.getDataKeranjang.ResponseGetDataKeranjang
import com.mutia.dsruput.preferences.PrefManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_keranjang.*
import kotlinx.android.synthetic.main.activity_keranjang.txtBackDetail
import kotlinx.android.synthetic.main.activity_update_keranjang.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class KeranjangActivity : AppCompatActivity() {

    lateinit var prefManager: PrefManager
    private var dialogView: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)

        prefManager = PrefManager(this)

        txtBackDetail.setOnClickListener {
//           startActivity(Intent(this, MenuActivity::class.java))
            onBackPressed()
        }

        showDataKeranjang()

        btnCheckout.setOnClickListener {
            startActivity(Intent(this, CheckoutActivity::class.java).putExtra("totalHarga", txtTotHarga.text.toString()))
        }

        btnTambahPesan.setOnClickListener {
            onBackPressed()
//            val intent = Intent(this, MenuActivity::class.java)
//            intent.putExtra("ID_OUTLET", 1)
//            startActivity(intent)

           // startActivity(Intent(this, MainActivity::class.java))
//            val message: String = "hai"
//            val data = Bundle()
//            data.putString(F.KEY_ACTIVITY, message)
//            val outlet = OutletFragment()
//            outlet.setArguments(data)
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.recylerOutlet, outlet)
//                .commit()
        }
    }

    private fun showDataKeranjang() {
        val id_user = prefManager.getValueInt("id").toString()

        val show = Network.service().getDataKeranjang(id_user)
        show.enqueue(object : Callback<ResponseGetDataKeranjang> {
            override fun onResponse(
                call: Call<ResponseGetDataKeranjang>,
                response: Response<ResponseGetDataKeranjang>
            ) {
                if (response.isSuccessful) {
                    val item = response.body()?.data

                    val banyak = item?.size

                    prefManager.save("jmlBagShop", banyak.toString().toInt())

                    var idCostumer = prefManager.getValueInt("id")

                    insertJumlahItemKeranjang(idCostumer.toString(), banyak.toString())

                    var totHarga = 0

                    for (i in 0..(banyak!! - 1)) {
                        totHarga = totHarga.plus(item.get(i)?.total_harga.toString().toInt())

                        if (i == (banyak - 1)) {
                            txtTotHarga.text = totHarga.toString()
                        }
                    }

                    val adapter = KeranjangAdapter(item, object : KeranjangAdapter.OnClickListener {

                        override fun hapus(item: DataItemKeranjang?) {
                            deleteKeranjang(item?.idKeranjang)
                        }

                        override fun detail(item: DataItemKeranjang?) {
                            showDialogUpdate(item!!)
                        }

                        override fun updateHarga() {
                            var totHarga = 0

                            for (i in 0..(banyak!! - 1)) {
                                totHarga = totHarga.plus(item.get(i)?.total_harga.toString().toInt())

                                if (i == (banyak - 1)) {
                                    txtTotHarga.text = totHarga.toString()
                                }
                            }
                        }

                    })

                    rvKeranjang.adapter = adapter
                    adapter.notifyDataSetChanged()

                } else {
                    Toast.makeText(this@KeranjangActivity, "Respon Get Data Gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseGetDataKeranjang>, t: Throwable) {
                Toast.makeText(this@KeranjangActivity, "Gagal Get Data", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showDialogUpdate(itemKeranjang: DataItemKeranjang) {

        val dialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.activity_update_keranjang, null)
        dialog.setView(view)

        view.txtRasaMenuKrj.setText(itemKeranjang.rasa)
        view.txtVarianMenuKrj.setText(itemKeranjang.varian)
        view.hargaSatuanKrj.setText(itemKeranjang.harga)
        view.txtHargaMenuKrj.setText(itemKeranjang.total_harga)
        view.txtJmlUpdateKrj.setText(itemKeranjang.jumlah)

        Picasso.get().load("http://192.168.43.84/dsruput/img/menu/" + itemKeranjang.gambar).into(
            view.imgUpdateKeranjang
        )

        var toppingTambahan = itemKeranjang.tambahan.toString()

        val arrayTopping = toppingTambahan.split(" + ").toTypedArray()

        var x = arrayTopping.size - 1

        for (i in 0..x)
        {
            if (arrayTopping[i] == "Brown Bubble"){
                view.chkBrownBubbleDialog.isChecked = true
            }
            if (arrayTopping[i] == "Rainbow Jelly"){
                view.chkRainbowJellyDialog.isChecked = true
            }
            if (arrayTopping[i] == "Regal"){
                view.chkRegalDialog.isChecked = true
            }
            if (arrayTopping[i] == "Jelly Coffee"){
                view.chkCoffeeJellyDialog.isChecked = true
            }
            if (arrayTopping[i] == "Popping Boba"){
                view.chkPoppingBobaDialog.isChecked = true
            }
            if (arrayTopping[i] == "Cream Cheese"){
                view.chkCreamCheeseDialog.isChecked = true
            }//else{
//                view.chkBrownBubbleDialog.isChecked = false
//                view.chkRainbowJellyDialog.isChecked = false
//                view.chkRegalDialog.isChecked = false
//                view.chkCoffeeJellyDialog.isChecked = false
//                view.chkPoppingBobaDialog.isChecked = false
//                view.chkCreamCheeseDialog.isChecked = false
//            }
        }

        view.btnAddKrj.setOnClickListener {

            var jmlDialog = view.txtJmlUpdateKrj.text.toString().toInt() + 1
            var totHargaDialog = view.txtHargaMenuKrj.text.toString().toInt() + view.hargaSatuanKrj.text.toString().toInt()

            view.txtJmlUpdateKrj.setText(jmlDialog.toString())
            view.txtHargaMenuKrj.setText(totHargaDialog.toString())
        }

        view.btnMinKrj.setOnClickListener {
            var jmlMin = view.txtJmlUpdateKrj.text.toString().toInt()

            if (jmlMin.equals(1)) {
                Toast.makeText(this, "Jumlah Pesanan Tidak Dapat Dikurangi", Toast.LENGTH_SHORT)
                    .show()
            } else {
                var totHargaMin = view.txtHargaMenuKrj.text.toString()
                    .toInt() - view.hargaSatuanKrj.text.toString().toInt()

                view.txtJmlUpdateKrj.setText((jmlMin - 1).toString())
                view.txtHargaMenuKrj.setText(totHargaMin.toString())
            }
        }

        view.btnOK.setOnClickListener {
            var id = itemKeranjang.idKeranjang.toString()

            var topping = " "

            if (view.chkBrownBubbleDialog.isChecked){
                if (topping != " "){
                    topping = topping + " + " + view.chkBrownBubbleDialog.text.toString()
                }else{
                    topping = view.chkBrownBubbleDialog.text.toString()
                }
            }

            if (view.chkRainbowJellyDialog.isChecked){
                if (topping != " "){
                    topping = topping + " + " + view.chkRainbowJellyDialog.text.toString()
                }else{
                    topping = view.chkRainbowJellyDialog.text.toString()
                }
            }

            if (view.chkCoffeeJellyDialog.isChecked){
                if (topping != " "){
                    topping = topping + " + " + view.chkCoffeeJellyDialog.text.toString()
                }else{
                    topping = view.chkCoffeeJellyDialog.text.toString()
                }

            }

            if (view.chkCreamCheeseDialog.isChecked){
                if (topping != " "){
                    topping = topping + " + " + view.chkCreamCheeseDialog.text.toString()
                }else{
                    topping = view.chkCreamCheeseDialog.text.toString()
                }
            }

            if (view.chkPoppingBobaDialog.isChecked){
                if (topping != " "){
                    topping = topping + " + " + view.chkPoppingBobaDialog.text.toString()
                }else{
                    topping = view.chkPoppingBobaDialog.text.toString()
                }
            }

            if (view.chkRegalDialog.isChecked){
                if (topping != " "){
                    topping = topping + " + " + view.chkRegalDialog.text.toString()
                }else{
                    topping = view.chkRegalDialog.text.toString()
                }
            }

            updateKeranjang(id, topping, view.txtJmlUpdateKrj.text.toString(), view.txtHargaMenuKrj.text.toString())

            dialogView?.dismiss()
            showDataKeranjang()
        }


        view.close.setOnClickListener {
            dialogView?.dismiss()
        }

        dialogView = dialog.create()
        dialogView?.show()
    }

    private fun showDialogUpdateAdd(itemKeranjang: DataItemKeranjang) {

        val dialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.activity_update_keranjang, null)
        dialog.setView(view)

        var jml = itemKeranjang.jumlah.toString().toInt() + 1
        var totHarga = itemKeranjang.total_harga.toString().toInt() + itemKeranjang.harga.toString().toInt()

        view.txtRasaMenuKrj.setText(itemKeranjang.rasa)
        view.txtVarianMenuKrj.setText(itemKeranjang.varian)
        //view.txtTambahanKrj.setText(itemKeranjang.tambahan)
        view.hargaSatuanKrj.setText(itemKeranjang.harga)
        view.txtHargaMenuKrj.setText(totHarga.toString())
        view.txtJmlUpdateKrj.setText(jml.toString())
        Picasso.get().load("http://192.168.43.84/dsruput/img/menu/" + itemKeranjang.gambar).into(
            view.imgUpdateKeranjang
        )

        var toppingTambahan = itemKeranjang.tambahan.toString()
        val arrayTopping = toppingTambahan.split(" + ").toTypedArray()

        var x = arrayTopping.size - 1

        for (i in 0..x) {
            if (arrayTopping[i] == "Brown Bubble") {
                view.chkBrownBubbleDialog.isChecked = true
            }
            if (arrayTopping[i] == "Rainbow Jelly") {
                view.chkRainbowJellyDialog.isChecked = true
            }
            if (arrayTopping[i] == "Regal") {
                view.chkRegalDialog.isChecked = true
            }
            if (arrayTopping[i] == "Jelly Coffee") {
                view.chkCoffeeJellyDialog.isChecked = true
            }
            if (arrayTopping[i] == "Popping Boba") {
                view.chkPoppingBobaDialog.isChecked = true
            }
            if (arrayTopping[i] == "Cream Cheese") {
                view.chkCreamCheeseDialog.isChecked = true
            }
        }

        view.btnAddKrj.setOnClickListener {

            var jmlAdd = view.txtJmlUpdateKrj.text.toString().toInt() + 1
            var totHargaAdd = view.txtHargaMenuKrj.text.toString().toInt() + view.hargaSatuanKrj.text.toString().toInt()

            view.txtJmlUpdateKrj.setText(jmlAdd.toString())
            view.txtHargaMenuKrj.setText(totHargaAdd.toString())
        }

        view.btnMinKrj.setOnClickListener {
            var jmlMin = view.txtJmlUpdateKrj.text.toString().toInt()

            if (jmlMin.equals(1)){
                Toast.makeText(this, "Jumlah Pesanan Tidak Dapat Dikurangi", Toast.LENGTH_SHORT).show()
            }else{
                var totHargaMin = view.txtHargaMenuKrj.text.toString().toInt() - view.hargaSatuanKrj.text.toString().toInt()

                view.txtJmlUpdateKrj.setText((jmlMin - 1).toString())
                view.txtHargaMenuKrj.setText(totHargaMin.toString())
            }
        }

        view.btnOK.setOnClickListener {
                var id = itemKeranjang.idKeranjang.toString()

                var topping = " "

                if (view.chkBrownBubbleDialog.isChecked){
                    if (topping != " "){
                        topping = topping + " + " + view.chkBrownBubbleDialog.text.toString()
                    }else{
                        topping = view.chkBrownBubbleDialog.text.toString()
                    }
                }

                if (view.chkRainbowJellyDialog.isChecked){
                    if (topping != " "){
                        topping = topping + " + " + view.chkRainbowJellyDialog.text.toString()
                    }else{
                        topping = view.chkRainbowJellyDialog.text.toString()
                    }
                }

                if (view.chkCoffeeJellyDialog.isChecked){
                    if (topping != " "){
                        topping = topping + " + " + view.chkCoffeeJellyDialog.text.toString()
                    }else{
                        topping = view.chkCoffeeJellyDialog.text.toString()
                    }

                }

                if (view.chkCreamCheeseDialog.isChecked){
                    if (topping != " "){
                        topping = topping + " + " + view.chkCreamCheeseDialog.text.toString()
                    }else{
                        topping = view.chkCreamCheeseDialog.text.toString()
                    }
                }

                if (view.chkPoppingBobaDialog.isChecked){
                    if (topping != " "){
                        topping = topping + " + " + view.chkPoppingBobaDialog.text.toString()
                    }else{
                        topping = view.chkPoppingBobaDialog.text.toString()
                    }
                }

                if (view.chkRegalDialog.isChecked){
                    if (topping != " "){
                        topping = topping + " + " + view.chkRegalDialog.text.toString()
                    }else{
                        topping = view.chkRegalDialog.text.toString()
                    }
                }

                updateKeranjang(id, topping, view.txtJmlUpdateKrj.text.toString(), view.txtHargaMenuKrj.text.toString())

                dialogView?.dismiss()
                showDataKeranjang()
        }

        view.close.setOnClickListener {
            dialogView?.dismiss()
        }

        dialogView = dialog.create()
        dialogView?.show()
    }

    private fun showDialogUpdateMin(itemKeranjang: DataItemKeranjang) {

        val dialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.activity_update_keranjang, null)
        dialog.setView(view)

        var jmlKurang = itemKeranjang.jumlah.toString().toInt() - 1
        var totHargaKurang = itemKeranjang.total_harga.toString().toInt() - itemKeranjang.harga.toString().toInt()

        view.txtRasaMenuKrj.setText(itemKeranjang.rasa)
        view.txtVarianMenuKrj.setText(itemKeranjang.varian)
       // view.txtTambahanKrj.setText(itemKeranjang.tambahan)
        view.hargaSatuanKrj.setText(itemKeranjang.harga)
        view.txtHargaMenuKrj.setText(totHargaKurang.toString())
        view.txtJmlUpdateKrj.setText(jmlKurang.toString())
        Picasso.get().load("http://192.168.43.84/dsruput/img/menu/" + itemKeranjang.gambar).into(
            view.imgUpdateKeranjang
        )

        view.btnAddKrj.setOnClickListener {

            var jmlAdd = view.txtJmlUpdateKrj.text.toString().toInt() + 1
            var totHargaAdd = view.txtHargaMenuKrj.text.toString().toInt() + view.hargaSatuanKrj.text.toString().toInt()

            view.txtJmlUpdateKrj.setText(jmlAdd.toString())
            view.txtHargaMenuKrj.setText(totHargaAdd.toString())
        }

        view.btnMinKrj.setOnClickListener {
            var jmlMin = view.txtJmlUpdateKrj.text.toString().toInt()

            if (jmlMin.equals(1)){
                Toast.makeText(this, "Jumlah Pesanan Tidak Dapat Dikurangi", Toast.LENGTH_SHORT).show()
            }else{
                var totHargaMin = view.txtHargaMenuKrj.text.toString().toInt() - view.hargaSatuanKrj.text.toString().toInt()

                view.txtJmlUpdateKrj.setText((jmlMin - 1).toString())
                view.txtHargaMenuKrj.setText(totHargaMin.toString())
            }

        }

        view.close.setOnClickListener {
            dialogView?.dismiss()
        }

        dialogView = dialog.create()
        dialogView?.show()
    }

    private fun deleteKeranjang(id_keranjang: String?){
        val hapus = Network.service().deleteKeranjang(id_keranjang ?: "")
        hapus.enqueue(object : Callback<ResponseGetDataKeranjang> {

            override fun onResponse(
                call: Call<ResponseGetDataKeranjang>,
                response: Response<ResponseGetDataKeranjang>
            ) {

                Toast.makeText(applicationContext, "Data Berhasil dihapus", Toast.LENGTH_SHORT)
                    .show()
                showDataKeranjang()

            }

            override fun onFailure(call: Call<ResponseGetDataKeranjang>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateKeranjang(id_keranjang: String?, tambahan: String?, jumlah: String?, total_harga: String?){
        val update = Network.service().updateKeranjang(id_keranjang ?: "", tambahan ?: "", jumlah ?: "", total_harga ?: "" )
        update.enqueue(object :Callback<ResponseGetDataKeranjang>{
            override fun onResponse(
                call: Call<ResponseGetDataKeranjang>,
                response: Response<ResponseGetDataKeranjang>
            ) {
                Toast.makeText(applicationContext, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
               // finish()
            }

            override fun onFailure(call: Call<ResponseGetDataKeranjang>, t: Throwable) {
                Toast.makeText(applicationContext, "Data gagal diupdate", Toast.LENGTH_SHORT).show()
               // finish()
            }

        })

    }

    private fun insertJumlahItemKeranjang(id_coustumer: String, jumlah: String){
        val insert = Network.service().insertBagShop(id_coustumer ?: "", jumlah ?: "")
        insert.enqueue(object : Callback<ResponseAction>{

            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                // Toast.makeText(this@KeranjangActivity, "Insert Berhasil", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                // Toast.makeText(this@KeranjangActivity, "Insert Gagal", Toast.LENGTH_SHORT).show()
            }

        })


    }

    private fun onCheck(): String{
        var topping = " "

        val view = layoutInflater.inflate(R.layout.activity_update_keranjang, null)

        if (view.chkBrownBubbleDialog.isChecked){
            if (topping != " "){
                topping = topping + " + " + view.chkBrownBubbleDialog.text.toString()
            }else{
                topping = view.chkBrownBubbleDialog.text.toString()
            }
        }

        if (view.chkRainbowJellyDialog.isChecked){
            if (topping != " "){
                topping = topping + " + " + view.chkRainbowJellyDialog.text.toString()
            }else{
                topping = view.chkRainbowJellyDialog.text.toString()
            }
        }

        if (view.chkCoffeeJellyDialog.isChecked){
            if (topping != " "){
                topping = topping + " + " + view.chkCoffeeJellyDialog.text.toString()
            }else{
                topping = view.chkCoffeeJellyDialog.text.toString()
            }

        }

        if (view.chkCreamCheeseDialog.isChecked){
            if (topping != " "){
                topping = topping + " + " + view.chkCreamCheeseDialog.text.toString()
            }else{
                topping = view.chkCreamCheeseDialog.text.toString()
            }
        }

        if (view.chkPoppingBobaDialog.isChecked){
            if (topping != " "){
                topping = topping + " + " + view.chkPoppingBobaDialog.text.toString()
            }else{
                topping = view.chkPoppingBobaDialog.text.toString()
            }
        }

        if (view.chkRegalDialog.isChecked){
            if (topping != " "){
                topping = topping + " + " + view.chkRegalDialog.text.toString()
            }else{
                topping = view.chkRegalDialog.text.toString()
            }
        }

        return topping
    }

}

