package ru.antonov.hotels.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.annotations.NonNull
import ru.antonov.hotels.network.HotelApi
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @NonNull
    @Provides
    fun apiClient(context: Context) = HotelApi(context)

    @Singleton
    @NonNull
    @Provides
    fun hotelsApi(apiClient: HotelApi) = apiClient.getHotelsApi()
}