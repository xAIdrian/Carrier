package com.example.carrier.dagger

import android.app.Application
import android.content.Context
import com.example.carrier.domain.local.ShiftDatabaseHelper
import com.example.carrier.domain.service.FakeInterceptor
import com.example.carrier.domain.service.RetrofitServiceBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideRetrofitClient(fakeInterceptor: FakeInterceptor) =
        RetrofitServiceBuilder(fakeInterceptor).getClient()

    @Provides
    @Singleton
    fun provideDatabase(context: Context) = ShiftDatabaseHelper(context)
}