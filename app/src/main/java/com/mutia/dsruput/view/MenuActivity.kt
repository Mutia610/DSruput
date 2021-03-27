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
import com.mutia.dsruput.model.getMenu.DataMenu
import com.mutia.dsruput.model.getMenu.ResponseGetMenu
import com.mutia.dsruput.preferences.PrefManager
import com.mutia.dsruput.view.dashboard.KeranjangActivity
import kotlinx.android.synthetic.main.activity_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuActivity : AppCompatActivity() {

    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Menampilkan jumlah item yang ada di keranjang di Ikon Keranjang
        prefManager = PrefManager(this)
        var jmlBagShop = prefManager.getValueInt("jmlBagShop")

        if (jmlBagShop > 0){
            jmlBeli.visibility = View.VISIBLE
            jmlBeli.text = jmlBagShop.toString()
        }else{
            jmlBeli.visibility = View.GONE
        }

        showMenu()

        icBagShop.setOnClickListener {
            startActivity(Intent(this, KeranjangActivity::class.java))
        }

        txtBackMenu.setOnClickListener {
            onBackPressed()
           // startActivity(Intent(this, MenuActivity::class.java))
        }
    }

    private fun showMenu() {
        val id : String = intent.getStringExtra("ID_OUTLET").toString()
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

                        override fun btnTambah(){
                            jmlBeli.visibility = View.VISIBLE
                            val jml = jmlBeli.text.toString().toInt()
                            jmlBeli.text = (jml + 1).toString()

//                            insertKeranjang(txt)
                        }

                        override fun addJml() {
                            var jmlAdd = jmlBeli.text.toString().toInt()
                            jmlBeli.text = (jmlAdd + 1).toString()
                        }

                        override fun minJml() {
                            var jmlMin = jmlBeli.text.toString().toInt()
                            if (jmlMin > 0){
                                jmlBeli.text = (jmlMin - 1).toString()
                            }else{
                                jmlBeli.visibility = View.GONE
                            }
                        }
                    })
                    recylerMenu.adapter = adapter
                }
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

//    private fun updateData(id: String?, nama: String?, nim: String?, jurusan: String?, nilai_keterampilan: String?, nilai_kebersihan: String?, nilai_ketelitian: String?, nilai_kejujuran: String?){
//
//        val input = Network.service().updateData(id ?: "",nama ?: "", nim ?: "", jurusan ?: "", nilai_keterampilan?: "", nilai_kebersihan?: "", nilai_ketelitian?: "", nilai_kejujuran ?: "")
//        input.enqueue(object : Callback<ResponseAction> {
//
//            override fun onResponse(
//                call: Call<ResponseAction>,
//                response: Response<ResponseAction>
//            ) {
//                Toast.makeText(applicationContext, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
//                finish()
//            }
//
//            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
//                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
}