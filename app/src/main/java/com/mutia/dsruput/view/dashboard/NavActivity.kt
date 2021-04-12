package com.mutia.dsruput.view.dashboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.GetLocationDetail
import com.example.easywaylocation.Listener
import com.example.easywaylocation.LocationData
import com.mutia.dsruput.R
import com.mutia.dsruput.preferences.PrefManager
import kotlinx.android.synthetic.main.activity_nav.*
import kotlinx.android.synthetic.main.fragment_outlet.*

class NavActivity : AppCompatActivity(), Listener, LocationData.AddressCallBack {

    var easyWayLocation: EasyWayLocation? = null
    var getLocationDetail: GetLocationDetail? = null

    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        val navController = Navigation.findNavController(this, R.id.home_nav_host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

        prefManager = PrefManager(this)
        // location = tvAddress

        getLocationDetail = GetLocationDetail(this, this)
        easyWayLocation = EasyWayLocation(this, false, this)

//        navController = Navigation.findNavController(view)

        if (permissionIsGranted()) {
            doLocationWork()
        } else {

        }
    }

    private fun doLocationWork() {
        easyWayLocation!!.startLocation()
    }

    fun permissionIsGranted(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    override fun locationOn() {
        Toast.makeText(this, "Location ON", Toast.LENGTH_SHORT).show();
    }

    override fun currentLocation(location: Location?) {
        val data = StringBuilder()
        data.append(location!!.latitude)
        data.append(" , ")
        data.append(location!!.longitude)
        //latLong!!.text = data --> Input latitude dan longtitude ke text view

        prefManager.save("latitude", location.latitude.toFloat())
        prefManager.save("longtitude", location.longitude.toFloat())

        getLocationDetail?.getAddress(location!!.latitude, location!!.longitude, "xyz")
    }

    override fun locationCancelled() {
        Toast.makeText(this, "Location Cancelled", Toast.LENGTH_SHORT).show();
    }

    override fun locationData(locationData: LocationData?) {
        prefManager.save("AlamatUser", locationData?.full_address!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EasyWayLocation.LOCATION_SETTING_REQUEST_CODE) {
            easyWayLocation!!.onActivityResult(resultCode)
        }
    }

    override fun onResume() {
        super.onResume()
        easyWayLocation!!.startLocation()
    }

    override fun onPause() {
        super.onPause()
        easyWayLocation!!.endUpdates()
    }
}