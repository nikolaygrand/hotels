package ru.antonov.hotels.cookies

import android.content.Context
import android.content.SharedPreferences
import ru.antonov.hotels.BuildConfig.DEFAULT_COOKIES_KEY

class Cookies(val context: Context) : ICookies {

    private var sharedData: SharedPreferences =
        context.getSharedPreferences(DEFAULT_COOKIES_KEY, Context.MODE_PRIVATE)

    override fun changeStorage(sharedKey: String) {
        sharedData = context.getSharedPreferences(sharedKey, Context.MODE_PRIVATE)
    }

    override fun set(key: String, value: String) {
        with(sharedData.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun get(key: String, defaultValue: String): String {
        return sharedData.getString(key, defaultValue) ?: defaultValue
    }

    override fun delete(key: String) {
        with(sharedData.edit()){
            remove(key)
            apply()
        }

    }

    override fun clear() {
        with(sharedData.edit()){
            clear()
            apply()
        }
    }
}