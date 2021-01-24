package com.example.carrier.ui.shiftdetails

import com.example.carrier.R
import com.example.carrier.common.BasePresenter
import com.example.carrier.common.ResourceProvider
import com.example.carrier.domain.CarrierRepository
import com.example.carrier.domain.service.RetrofitServiceBuilder.Companion.SUCCESS_CODE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ShiftDetailsPresenter @Inject constructor(
    private val repository: CarrierRepository,
    private val resourceProvider: ResourceProvider
) : BasePresenter<ShiftDetailsMvpContract.View>(), ShiftDetailsMvpContract.Presenter {

    private val disposable = CompositeDisposable()

    override fun getShifts() {
        disposable.add(
            repository.getShifts()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    mvpView?.showLoading(true)
                }.subscribe(
                    {
                        mvpView?.showLoading(false)
                        if (it.status == SUCCESS_CODE) {
                            mvpView?.showCarrierShift(it.shift)
                        } else {
                            mvpView?.errorMessage(it.message)
                        }
                    },
                    {
                        mvpView?.showLoading(false)
                        mvpView?.errorMessage(
                            it.message
                                ?: resourceProvider.getStringRes(R.string.somethings_went_wrong)
                        )
                    }
                )
        )
    }

    override fun sendHello() {
        disposable.add(repository.postMessage()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mvpView?.showLoading(true)
            }.subscribe(
                {
                    mvpView?.showLoading(false)
                },
                {
                    mvpView?.showLoading(false)
                    mvpView?.errorMessage(resourceProvider.getStringRes(R.string.somethings_went_wrong))
                }
            ))
    }

    override fun unsubscribe(view: ShiftDetailsMvpContract.View) {
        disposable.clear()
        super.unsubscribe(view)
    }
}