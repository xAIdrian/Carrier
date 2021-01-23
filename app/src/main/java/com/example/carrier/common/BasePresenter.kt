package com.example.carrier.common

import androidx.annotation.UiThread

/**
 * Base class that implements the MainPresenter interface and provides a base implementation for
 * subscribe() and unsubscribe().
 * It keeps a reference to the view that can be accessed from the children classes
 * by calling getMvpView(), ensuring consistent references
 */
abstract class BasePresenter<V> : Presenter<V> {
    var mvpView: V? = null
        private set

    /**
     * Lifecycle methods
     */
    @UiThread
    fun onCreate() {
    }

    @UiThread
    fun onResume() {
    }

    @UiThread
    fun onPause() {
    }

    @UiThread
    fun onStop() {
    }

    @UiThread
    fun onStart() {
    }

    @UiThread
    fun onDestroy() {
    }

    override fun subscribe(mvpView: V) {
        this.mvpView = mvpView
    }

    override fun unsubscribe(view: V) {
        mvpView = null
    }

    val isViewAttached: Boolean
        get() = mvpView != null

    /**
     * If the view is not attached throw a custom error
     */
    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException : RuntimeException(
        "Please call your presenter's .subscribe(MvpView) before" +
                " requesting data to the MainPresenter"
    )
}