package com.mutia.dsruput.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mutia.dsruput.R
import com.mutia.dsruput.adapter.MenuAdapter
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.action.ResponseAction
import com.mutia.dsruput.model.getDataKeranjang.ResponseGetDataKeranjang
import com.mutia.dsruput.model.getMenu.DataMenu
import com.mutia.dsruput.model.getMenu.ResponseGetMenu
import com.mutia.dsruput.preferences.PrefManager
import com.mutia.dsruput.view.dashboard.KeranjangActivity
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuActivity : AppCompatActivity() {

    lateinit var prefManager: PrefManager
    var statusKeranjang: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Menampilkan jumlah item yang ada di keranjang di Ikon Keranjang
        prefManager = PrefManager(this)

        var jmlKrj = prefManager.getValueInt("jmlBagShop")

//        if (jmlKrj > 0){
//            jmlBeli.visibility = View.VISIBLE
//            jmlBeli.text = jmlKrj.toString()
//        }else{
//            jmlBeli.visibility = View.GONE
//        }

        showMenu()
        showDataKeranjang()

        icBagShop.setOnClickListener {
            startActivity(Intent(this, KeranjangActivity::class.java))
        }

        txtBackMenu.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showMenu() {
        val id : String = intent.getStringExtra("ID_OUTLET").toString()
        val id_user = prefManager.getValueInt("id").toString()
        val namaOutlet : String = intent.getStringExtra("NAMA_OUTLET").toString()

        titleOutlet.setText(namaOutlet)

        val listMenu = Network.service().getMenu(id)
        listMenu.enqueue(object : Callback<ResponseGetMenu> {

            override fun onFailure(call: Call<ResponseGetMenu>, t: Throwable) {
                Log.d("eror", "eror : " + t + " cal :" + call);
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()

            }

            override fun onResponse(
                call: Call<ResponseGetMenu>,
                response: Response<ResponseGetMenu>
            ) {
                if (response.isSuccessful){
                    val item = response.body()?.data
                    val banyak = item!!.size - 1

                    val adapter = MenuAdapter(item?.sortedWith(compareBy { it?.varian }), object : MenuAdapter.OnClickListener{
                        override fun detailMenu(item: DataMenu?) {
                            val intent = Intent(this@MenuActivity, DetailMenu::class.java)
                            intent.putExtra("data", item)
                            intent.putExtra("outlet", namaOutlet)
                            intent.putExtra("id_outlet", id)
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                            finish()
                        }

                        override fun btnTambah(data: DataMenu?){
                            jmlBeli.visibility = View.VISIBLE
                            val jml = jmlBeli.text.toString().toInt()
                            jmlBeli.text = (jml + 1).toString()

                            val id_menu = data?.idMenu.toString()
                            val harga = data?.harga.toString()

                            insertKeranjang(id_menu, id, id_user, " ", "1", harga)
                        }

                        override fun addJml(data: DataMenu?) {
//                            var jmlAdd = jmlBeli.text.toString().toInt()
//                            jmlBeli.text = (jmlAdd + 1).toString()

                            val idMenu = data?.idMenu.toString()
                            prefManager.save("id_menu", idMenu)

                            showKeranjangTambah()

                        }

                        override fun minJml() {
                            var jmlMin = jmlBeli.text.toString().toInt()
//                            if (jmlMin > 0){
//                                jmlBeli.text = (jmlMin - 1).toString()
//                            }else{
//                                jmlBeli.visibility = View.GONE
//                            }

                            if (jmlMin < 1){
                                jmlBeli.visibility = View.GONE
                            }

                            showKeranjangKurang()
                        }
                    })
                    recylerMenu.adapter = adapter
                }
            }
        })
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

                    val banyak = item?.size!!.toInt()

                    if (banyak != 0){
                        jmlBeli.visibility = View.VISIBLE
                        jmlBeli.text = banyak.toString()
                    }else{
                        jmlBeli.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(this@MenuActivity, "Gagal mendapatkan jumlah item", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseGetDataKeranjang>, t: Throwable) {
                Toast.makeText(this@MenuActivity, "Gagal Get Data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showKeranjangTambah() {
        val id_user = prefManager.getValueInt("id").toString()
        val id : String = intent.getStringExtra("ID_OUTLET").toString()

        val show = Network.service().getDataKeranjang(id_user)
        show.enqueue(object : Callback<ResponseGetDataKeranjang> {
            override fun onResponse(
                call: Call<ResponseGetDataKeranjang>,
                response: Response<ResponseGetDataKeranjang>
            ) {
                if (response.isSuccessful) {
                    val item = response.body()?.data
                    val banyak = item!!.size - 1
                    val id_menu = prefManager.getValueString("id_menu").toString()

                    for (i in 0..banyak) {
                        val idMenu = item.get(i)?.idMenu.toString()
                        val topping = item.get(i)?.tambahan.toString()
                        val jml = item.get(i)?.jumlah!!.toInt()
                        val harga = item.get(i)?.harga!!.toInt()
                        val totHarga = item.get(i)?.total_harga!!.toInt()

                        if (idMenu == id_menu && topping == " ") {
//                            statusKeranjang = 1
                            updateJmlKeranjang(
                                id_menu,
                                (jml + 1).toString(),
                                (totHarga + harga).toString()
                            )
                        }//else{
//                            if (i == banyak){
//                                insertKeranjang(id_menu, id, id_user, " ", "1", harga.toString())
//                            }
//                        }
                    }
                } else {
                    Toast.makeText(this@MenuActivity, "Gagal mendapatkan jumlah item", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseGetDataKeranjang>, t: Throwable) {
                Toast.makeText(this@MenuActivity, "Gagal Get Data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showKeranjangKurang() {
        val id_user = prefManager.getValueInt("id").toString()
        val id : String = intent.getStringExtra("ID_OUTLET").toString()

        val show = Network.service().getDataKeranjang(id_user)
        show.enqueue(object : Callback<ResponseGetDataKeranjang> {
            override fun onResponse(
                call: Call<ResponseGetDataKeranjang>,
                response: Response<ResponseGetDataKeranjang>
            ) {
                if (response.isSuccessful) {
                    val item = response.body()?.data
                    val banyak = item!!.size - 1
                    val id_menu = prefManager.getValueString("id_menu").toString()

                    for (i in 0..banyak) {
                        val idMenu = item.get(i)?.idMenu.toString()
                        val topping = item.get(i)?.tambahan.toString()
                        val jml = item.get(i)?.jumlah!!.toInt()
                        val harga = item.get(i)?.harga!!.toInt()
                        val totHarga = item.get(i)?.total_harga!!.toInt()

                        if (idMenu == id_menu && topping == " ") {
                            statusKeranjang = 2
                            updateJmlKeranjang(
                                id_menu,
                                (jml - 1).toString(),
                                (totHarga + harga).toString()
                            )
                        }

//                        if (idMenu != id_menu){
//                            if (i == banyak){
//                                insertKeranjang(id_menu, id, id_user, " ", "1", harga.toString())
//                            }
//                        }
                    }
                } else {
                    Toast.makeText(this@MenuActivity, "Gagal mendapatkan jumlah item", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseGetDataKeranjang>, t: Throwable) {
                Toast.makeText(this@MenuActivity, "Gagal Get Data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun insertKeranjang(id_menu: String, id_outlet: String, id_user: String, tambahan: String, jumlah: String, total_harga: String){
        val input = Network.service().insertKeranjang(id_menu ?: "", id_outlet ?: "", id_user ?: "",tambahan ?: "",jumlah ?: "", total_harga ?: "")
        input.enqueue(object : Callback<ResponseAction>{
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(this@MenuActivity, "Pesanan Anda Telah Ditambah", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(this@MenuActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun updateJmlKeranjang(id_menu: String?, jumlah: String?, total_harga: String?){

        val input = Network.service().updateJmlKeranjang(id_menu ?: "",jumlah ?: "", total_harga ?: "")
        input.enqueue(object : Callback<ResponseGetDataKeranjang> {
            override fun onResponse(
                call: Call<ResponseGetDataKeranjang>,
                response: Response<ResponseGetDataKeranjang>
            ) {
//                Toast.makeText(applicationContext, "Berhasil Update", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseGetDataKeranjang>, t: Throwable) {
                Toast.makeText(applicationContext, "Gagal Update Item", Toast.LENGTH_SHORT).show()
            }
        })
    }
}