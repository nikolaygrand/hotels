package ru.antonov.hotels.cache

import android.content.Context
import android.util.Log
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.BoxStoreBuilder
import io.objectbox.android.AndroidObjectBrowser
import ru.antonov.hotels.BuildConfig
import ru.antonov.hotels.application.HotelApplication
import ru.antonov.hotels.data.MyObjectBox

class Cache constructor(context: Context) {
    private var boxStore: BoxStore =
        MyObjectBox.builder().androidContext(context.applicationContext).build()
}