package ru.antonov.hotels.application

import android.app.Application
import io.objectbox.android.ObjectBoxLiveData
import ru.antonov.hotels.di.AppComponent
import ru.antonov.hotels.di.AppModule
import ru.antonov.hotels.di.DaggerAppComponent

class HotelApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Инициализация кэша
        //ObjectBox.init(this)
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)
    }

    companion object {
        val TAG = HotelApplication::class.java.simpleName
        lateinit var appComponent: AppComponent
    }
}