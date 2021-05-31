package com.example.geolocationapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission()
            else {
                tv_service_state.text = "Сервис включен"
                sendServiceCommand("start_service")
            }
        }
        btn_stop.setOnClickListener {
            tv_service_state.text = "Сервис выключен"
            sendServiceCommand("stop_service")
        }
    }

    private fun sendServiceCommand(command: String) {
        val intent = Intent(this, LocationService::class.java)
        intent.putExtra("command", command)
        startService(intent)
    }

    private fun requestLocationPermission() {
        this.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 55)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 55
            && permissions.first() == Manifest.permission.ACCESS_FINE_LOCATION
            && grantResults.first() == PackageManager.PERMISSION_GRANTED
        ) {
            tv_service_state.text = "Сервис включен"
            sendServiceCommand("start_service")
        }
    }

}