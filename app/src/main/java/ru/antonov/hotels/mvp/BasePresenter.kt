package ru.antonov.hotels.mvp

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : MvpView> : MvpBasePresenter<V>() {
    protected val disposable = CompositeDisposable()

    override fun destroy() {
        disposable.dispose()
    }

    override fun detachView() {
        disposable.clear()
    }

    override fun detachView(retainInstance: Boolean) {
        disposable.clear()
    }

    override fun attachView(view: V) {
    }
}