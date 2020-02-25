package ru.antonov.hotels.di

import dagger.Module
import dagger.Provides
import io.reactivex.annotations.NonNull
import ru.antonov.hotels.network.HotelApi
import ru.antonov.hotels.repository.HotelsRepository
import ru.antonov.hotels.repository.IHotelsRepository
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @NonNull
    @Provides
    fun repository(apiClient: HotelApi): IHotelsRepository = HotelsRepository(apiClient)
}