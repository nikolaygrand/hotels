package ru.antonov.hotels.cookies

interface ICookies {
    fun changeStorage(sharedKey: String)
    fun set(key: String, value: String)
    fun get(key: String, defaultValue: String): String
    fun delete(key: String)
    fun clear()
}