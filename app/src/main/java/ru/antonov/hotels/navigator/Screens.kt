package ru.antonov.hotels.navigator

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.antonov.hotels.screens.home.HomeFragment
import ru.antonov.hotels.screens.hotel.HotelFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class HomeScreen: SupportAppScreen() {
        override fun getFragment(): Fragment {
            return HomeFragment()
        }
    }

    class HotelScreen(val hotelId: Long): SupportAppScreen() {
        override fun getFragment(): Fragment {
            val bundle = Bundle().apply {
                putLong(HotelFragment.HOTEL_ID, hotelId)
            }

            return HotelFragment().apply {
               arguments = bundle
            }
        }
    }
}