package com.mutia.dsruput.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mutia.dsruput.R
import com.mutia.dsruput.adapter.OutletAdapater
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.getData.ResponseGetData
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.fragment_outlet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OutletFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showData()

    }

    private fun showData() {
        val listOutlet = Network.service().getData()
        listOutlet.enqueue(object : Callback<ResponseGetData> {

            override fun onResponse(
                call: Call<ResponseGetData>,
                response: Response<ResponseGetData>
            ) {
                if (response.isSuccessful){
                    val item = response.body()?.data

                    val adapter = OutletAdapater(item, object : OutletAdapater.OnClickListener {

                        override fun menu(kodeOutlet: String?) {
                            val intent = Intent(context, MenuActivity::class.java)
                            intent.putExtra("ID_OUTLET", kodeOutlet)
                            startActivity(intent)
                        }
                    })
                    recylerOutlet.adapter = adapter
                    /* recylerOutlet.setHasFixedSize(true);
                     recylerOutlet.setAdapter(adapter);
                     val llm = LinearLayoutManager(context)
                     llm.orientation = LinearLayoutManager.VERTICAL
                     recylerOutlet.setLayoutManager(llm);*/
                }
            }
            override fun onFailure(call: Call<ResponseGetData>, t: Throwable) {
                Log.d("eror", "eror : "+t+" cal :"+call);
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}


