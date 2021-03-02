package com.mutia.dsruput.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mutia.dsruput.R
import com.mutia.dsruput.adapter.MenuAdapter
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.getMenu.DataMenu
import com.mutia.dsruput.model.getMenu.ResponseGetMenu
import com.mutia.dsruput.preferences.PrefManager
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.item_menu.*
import kotlinx.android.synthetic.main.item_outlet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        showMenu()

        txtBackMenu.setOnClickListener {
            onBackPressed()
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
                            startActivity(intent)
                        }

                        override fun btnTambah(){
                            Toast.makeText(this@MenuActivity, "Pesanan Anda Telah Ditambah", Toast.LENGTH_SHORT).show()
                        }
                    })

                    recylerMenu.adapter = adapter
                }
            }

        })
    }
}