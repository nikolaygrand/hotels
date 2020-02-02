package ru.antonov.hotels.activity

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface MainView: MvpView {
    fun back(): Observable<Unit>
}