package ru.antonov.hotels.screens.home

enum class SortEnum(val value: Int) {
    NONE(0), ASC(1), DESC(2);

    companion object {
        private val values = values()
        fun getByValue(value: Int) = values.firstOrNull { it.value == value }
    }
}