package ru.antonov.hotels.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.hotel_item.view.*
import ru.antonov.hotels.R
import ru.antonov.hotels.data.HotelModel

class HotelsAdapter: RecyclerView.Adapter<HotelsViewHolder>() {
    private var dataset = ArrayList<HotelModel>()
    var subjectItemClick = PublishSubject.create<HotelModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hotel_item, parent, false)
        return HotelsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: HotelsViewHolder, position: Int) {
        val hotel = dataset[position]
        val view = holder.itemView

        view.setOnClickListener { subjectItemClick.onNext(hotel) }

        view.tvTitle.text         = hotel.name
        view.tvAddress.text       = hotel.address
        view.tvAddress.text       = hotel.address
        view.rbHotel.rating       = hotel.stars!!
        view.tvDistanceValue.text = hotel.distance.toString()
        view.tvRoomsValue.text    = hotel.parseSuitesAvailability()
    }

    fun setDataset(data: ArrayList<HotelModel>) {
        dataset.clear()
        dataset.addAll(data)
    }

    fun getDataset(): ArrayList<HotelModel> {
        return dataset
    }
}