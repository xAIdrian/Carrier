package com.example.carrier.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carrier.CarrierApp
import com.example.carrier.dagger.DaggerApplicationComponent

abstract class MvpActivity<P : BasePresenter<V>?, V> : AppCompatActivity() {

    abstract fun getPresenter(): P
    abstract fun getMvpView(): V

    open lateinit var appComponent: DaggerApplicationComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (applicationContext as CarrierApp).appComponent
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (getPresenter() != null) {
            getPresenter()?.subscribe(getMvpView())
            getPresenter()?.onStart()
        }
    }

    public override fun onResume() {
        super.onResume()
        getPresenter()?.onResume()
    }

    public override fun onPause() {
        getPresenter()?.onPause()
        super.onPause()
    }

    public override fun onStop() {
        if (getPresenter() != null) {
            getPresenter()?.onStop()
            getPresenter()?.unsubscribe(getMvpView())
        }
        super.onStop()
    }

    public override fun onDestroy() {
        getPresenter()?.onDestroy()
        super.onDestroy()
    }
}