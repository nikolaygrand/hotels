package ru.antonov.hotels.screens.home

enum class SortFieldsEnum(val value: Int) {
    NONE(0), DISTANCE(1), FREE_ROOMS(2);

    companion object {
        private val values = values()
        fun getByValue(value: Int) = values.firstOrNull { it.value == value }
    }
}