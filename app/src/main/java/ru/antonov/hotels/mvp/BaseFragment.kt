package ru.antonov.hotels.mvp

import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.hannesdorfmann.mosby3.mvp.MvpView

abstract class BaseFragment<V : MvpView, P : BasePresenter<V>> :
    MvpFragment<V, P>()