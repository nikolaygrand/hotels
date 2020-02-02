package ru.antonov.hotels.screens.home

import ru.antonov.hotels.data.HotelModel

data class SortModel(val field: SortFieldsEnum, val direction: SortEnum, val data: ArrayList<HotelModel>)