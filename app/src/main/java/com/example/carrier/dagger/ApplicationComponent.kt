package com.example.carrier.dagger

import com.example.carrier.ui.shiftdetails.ShiftDetailsComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ActivityModule::class])
interface ApplicationComponent {
    fun shiftDetailsActivityComponent(): ShiftDetailsComponent.Factory
}