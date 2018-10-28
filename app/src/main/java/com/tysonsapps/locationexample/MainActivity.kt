package com.tysonsapps.locationexample

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), LocationListener {

    val FINE_LOCATION_REQUEST_CODE = 99
    lateinit var locationManager: LocationManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        startButton.setOnClickListener {
            checkForGPS()
        }

        stopButton.setOnClickListener {
            stopGPS()
        }
    }

    fun checkForGPS() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION

        if(checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(arrayOf(permission), FINE_LOCATION_REQUEST_CODE)
        }
        else {
            startGPS()
        }
    }

    @SuppressLint("MissingPermission")
    fun startGPS() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0L,0F,this)
    }

    fun stopGPS() {
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location?) {
        location?.let {
            statusTextView.text = "${location.latitude}, ${location.longitude}, accuracy: ${location.accuracy}"
        }
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        //not used
    }

    override fun onProviderEnabled(p0: String?) {
        //not used
    }

    override fun onProviderDisabled(p0: String?) {
        //not used
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == FINE_LOCATION_REQUEST_CODE) {
            if(grantResults != null && grantResults.count() == 1) {
                startGPS()
            }
            else {
                toast("user denied location access")
            }
        }
        else {
            toast("unhandled request code")
        }
    }
}