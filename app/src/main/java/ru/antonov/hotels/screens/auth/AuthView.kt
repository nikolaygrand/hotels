package ru.antonov.hotels.screens.auth

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface AuthView : MvpView {
    fun authSuccess(): Observable<Unit>
}