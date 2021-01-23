package com.example.carrier.ui.shiftdetails

import com.example.carrier.dagger.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface ShiftDetailsComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ShiftDetailsComponent
    }
    fun inject(shiftDetailsActivity: ShiftDetailsActivity)
}