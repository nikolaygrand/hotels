package ru.antonov.hotels.application

import android.app.Application
import ru.antonov.hotels.di.AppComponent
import ru.antonov.hotels.di.AppModule
import ru.antonov.hotels.di.DaggerAppComponent

class HotelApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}