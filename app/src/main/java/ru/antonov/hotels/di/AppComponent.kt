package ru.antonov.hotels.di

import dagger.Component
import ru.antonov.hotels.activity.MainActivity
import ru.antonov.hotels.activity.MainPresenter
import ru.antonov.hotels.application.HotelApplication
import ru.antonov.hotels.navigator.Navigator
import ru.antonov.hotels.screens.home.HomePresenter
import ru.antonov.hotels.screens.hotel.HotelPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, NavigationModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(application: HotelApplication)
    fun inject(navigator: Navigator)

    fun inject(presenter: MainPresenter)
    fun inject(presenter: HomePresenter)
    fun inject(presenter: HotelPresenter)
}