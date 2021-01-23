package com.example.carrier.common

/**
 * Every presenter in the app must either implement this interface
 * or extend BasePresenter stating the MvpView type that wants
 * to be attached with.
 */
interface Presenter<V> {
    fun subscribe(mvpView: V)
    fun unsubscribe(view: V)
}