package com.mutia.dsruput.view

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.GetLocationDetail
import com.example.easywaylocation.Listener
import com.example.easywaylocation.LocationData
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.mutia.dsruput.R
import com.mutia.dsruput.adapter.OutletAdapater
import com.mutia.dsruput.config.Network
import com.mutia.dsruput.model.getData.ResponseGetData
import com.mutia.dsruput.preferences.PrefManager
import kotlinx.android.synthetic.main.fragment_outlet.*
import kotlinx.android.synthetic.main.item_outlet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.mutia.dsruput.model.getData.DataItem
import kotlin.math.log


class OutletFragment : Fragment(){ //, Listener, LocationData.AddressCallBack

//    lateinit var navController: NavController
//
//    var easyWayLocation: EasyWayLocation? = null
//    private var location: TextView? = null
//    var getLocationDetail: GetLocationDetail? = null

    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        showData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_outlet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())

        val alamat_user = prefManager.getValueString("AlamatUser").toString()
        textAlamatOutlet.text = alamat_user

        expandMore.setOnClickListener {
            relative2Outlet.visibility = View.VISIBLE
            closeOutlet.visibility = View.VISIBLE
            expandMore.visibility = View.GONE
        }

        closeOutlet.setOnClickListener {
            relative2Outlet.visibility = View.GONE
            closeOutlet.visibility = View.GONE
            expandMore.visibility = View.VISIBLE
        }
//        location = requireView().findViewById(R.id.tvAddress)
//        // location = tvAddress
//        getLocationDetail = GetLocationDetail(this, context)
//        easyWayLocation = EasyWayLocation(context, false, this)
//
//        navController = Navigation.findNavController(view)
//
//        if (permissionIsGranted()) {
//            doLocationWork()
//        } else {
//            // Permission not granted, ask for it
//            //testLocationRequest.requestPermission(121);
//        }
//
//        Toast.makeText(context, "lat :"+prefManager.getValueFloat("latitude")+" long : "+prefManager.getValueFloat("longtitude"), Toast.LENGTH_LONG).show()

    }

    private fun showData() {
        val listOutlet = Network.service().getData()
        listOutlet.enqueue(object : Callback<ResponseGetData> {

            override fun onResponse(
                call: Call<ResponseGetData>,
                response: Response<ResponseGetData>
            ) {
                if (response.isSuccessful) {
                    val item = response.body()?.data

                 //   prefManager = context?.let { PrefManager(it) }!!

                    var ltdUser = prefManager?.getValueFloat("latitude")
                    var ntdUser = prefManager?.getValueFloat("longtitude")

                    for (i in 0..(item!!.size)-1) {

                        var ltdOutlet = response.body()?.data?.get(i)?.latitude
                        var ntdOutlet = response.body()?.data?.get(i)?.longtitude

                        var latLngOutlet: LatLng = LatLng(ltdOutlet!!.toDouble(), ntdOutlet!!.toDouble())
                        val latLngUser: LatLng = LatLng(ltdUser.toString().toDouble(), ntdUser.toString().toDouble())
                        var distance: Double? = null

                        distance = SphericalUtil.computeDistanceBetween(latLngOutlet, latLngUser)
                        var jarak = String.format("%.2f", distance!! / 1000) + " Km"

                        response.body()?.data?.get(i)?.jarak = jarak
                    }

                    val adapter = OutletAdapater(item?.sortedBy {it?.jarak },

                            object : OutletAdapater.OnClickListener {

                                override fun menu(kodeOutlet: String?, namaOutlet: String?) {
                                    val intent = Intent(context, MenuActivity::class.java)
                                    intent.putExtra("ID_OUTLET", kodeOutlet)
                                    intent.putExtra("NAMA_OUTLET", namaOutlet)
                                    startActivity(intent)
                                }
                            })
                    recylerOutlet.adapter = adapter
                }
            }

            override fun onFailure(call: Call<ResponseGetData>, t: Throwable) {
                Log.d("eror", "eror : " + t + " cal :" + call);
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}