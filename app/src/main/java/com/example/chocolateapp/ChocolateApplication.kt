package com.example.chocolateapp

import android.app.Application
import com.example.chocolateapp.data.AppContainer
import com.example.chocolateapp.data.AppDataContainer

class ChocolateApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

    }
}