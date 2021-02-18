package com.mutia.dsruput.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mutia.dsruput.R
import com.mutia.dsruput.adapter.MenuAdapter
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.getMenu.ResponseGetMenu
import kotlinx.android.synthetic.main.activity_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        showMenu()
    }

    private fun showMenu() {
        val id : String = intent.getStringExtra("ID_OUTLET").toString()

        val listMenu = Network.service().getMenu(id)
        listMenu.enqueue(object : Callback<ResponseGetMenu> {

            override fun onResponse(
                call: Call<ResponseGetMenu>,
                response: Response<ResponseGetMenu>
            ) {
                if (response.isSuccessful) {
                    val item = response.body()?.data

                    val adapter = MenuAdapter(item?.sortedWith(compareBy { it?.varian }))

                    // val adapter = MenuAdapter(item)
                    recylerMenu.adapter = adapter

                    /*recylerMenu.setHasFixedSize(true);
                    recylerMenu.setAdapter(adapter);
                    val llm = LinearLayoutManager(applicationContext)
                    llm.orientation = LinearLayoutManager.VERTICAL
                    recylerMenu.setLayoutManager(llm); */
                }
            }

            override fun onFailure(call: Call<ResponseGetMenu>, t: Throwable) {
                Log.d("eror", "eror : " + t + " cal :" + call);
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}