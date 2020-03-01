package ru.antonov.hotels.di

import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import ru.antonov.hotels.cookies.ICookies
import ru.antonov.hotels.interactors.HomeInteractor
import ru.antonov.hotels.interactors.HotelInteractor
import ru.antonov.hotels.interactors.IHomeInteractor
import ru.antonov.hotels.interactors.IHotelInteractor
import ru.antonov.hotels.repository.IHotelsRepository
import javax.inject.Singleton

@Module
class InteractorModule {

    @Singleton
    @NonNull
    @Provides
    fun home(cookies: ICookies, repository: IHotelsRepository): IHomeInteractor =
        HomeInteractor(cookies, repository)

    @Singleton
    @NonNull
    @Provides
    fun hotel(cookies: ICookies, repository: IHotelsRepository): IHotelInteractor =
        HotelInteractor(cookies, repository)
}