package com.example.carrier

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.carrier.dagger.ApplicationModule
import com.example.carrier.dagger.DaggerApplicationComponent

class CarrierApp: Application() {
    lateinit var appComponent: DaggerApplicationComponent

    override fun onCreate() {
        this.appComponent = this.initDagger() as DaggerApplicationComponent
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private fun initDagger() = DaggerApplicationComponent.builder()
        .applicationModule(ApplicationModule(this))
        .build()
}