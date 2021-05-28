package com.example.geolocationapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener {
            tv_service_state.text = "Сервис включен"
        }
        btn_stop.setOnClickListener {
            tv_service_state.text = "Сервис выключен"
        }
    }
}