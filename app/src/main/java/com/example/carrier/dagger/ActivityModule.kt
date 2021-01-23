package com.example.carrier.dagger

import com.example.carrier.ui.shiftdetails.ShiftDetailsComponent
import com.example.carrier.ui.shiftdetails.ShiftDetailsPresenter
import dagger.Module
import dagger.Provides

@Module(subcomponents = [ShiftDetailsComponent::class])
class ActivityModule {
//    @ActivityScope
//    @Provides
//    fun providesFactory(): ShiftDetailsPresenter{
//        return ParentActivityViewModel.Factory()
//    }
}