package ru.antonov.hotels.mvp

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : MvpView> : MvpPresenter<V> {
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