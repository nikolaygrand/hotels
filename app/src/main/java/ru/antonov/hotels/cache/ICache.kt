package ru.antonov.hotels.cache

interface ICache {
    fun <T> getItemsByModel(modelclass: T) : List<T>
}