package ru.antonov.hotels.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.annotations.NonNull
import ru.antonov.hotels.cache.Cache
import ru.antonov.hotels.cookies.Cookies
import ru.antonov.hotels.cookies.ICookies
import javax.inject.Singleton

@Module
class CacheModule {
    @Singleton
    @NonNull
    @Provides
    fun cache(context: Context) = Cache(context)

    @Singleton
    @NonNull
    @Provides
    fun cookies(context: Context): ICookies = Cookies(context)
}