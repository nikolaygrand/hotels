package ru.antonov.hotels.screens.hotel

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.home_hotel.*
import kotlinx.android.synthetic.main.hotel_item.*
import ru.antonov.hotels.BuildConfig
import ru.antonov.hotels.R
import ru.antonov.hotels.data.HotelDetailsModel
import ru.antonov.hotels.mvp.BaseFragment

class HotelFragment : BaseFragment<HotelView, HotelPresenter>(), HotelView {
    private val subjectHotel = PublishSubject.create<Long>()
    private val subjectBack = PublishSubject.create<Unit>()
    private var subjectRefresh = PublishSubject.create<Unit>()
    private var hotelId: Long = 0

    override fun createPresenter() = HotelPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        hotelId = arguments!!.getLong(HOTEL_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.home_hotel, null, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subjectHotel.onNext(hotelId)
        (toolbar as Toolbar).setNavigationOnClickListener { subjectBack.onNext(Unit) }

        with(srlHotel) {
            setOnRefreshListener {
                isRefreshing = false
                subjectHotel.onNext(hotelId)
                skeletonLayout.showSkeleton()
            }
        }

        skeletonLayout.showSkeleton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("HOTEL_ID", hotelId)
        outState.putInt("FRAGMENT_ID", this.id)
    }

    override fun loadHotel(hotel: HotelDetailsModel) {

        tvTitle.text = hotel.name
        tvAddress.text = hotel.address
        tvAddress.text = hotel.address
        rbHotel.rating = hotel.stars!!
        tvDistanceValue.text = hotel.distance.toString()
        tvRoomsValue.text = hotel.parseSuitesAvailability()

        skeletonLayout.showOriginal()
        /*
        initPointMap(hotel)
        mvHotelMap.visibility = View.VISIBLE
        */

        val imagePath = "${BuildConfig.IMAGE_URL}${hotel.image}"
        val listener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                error(Throwable("Ошибка при загрузке изображения"))
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

        }
        Glide.with(this)
            .load(imagePath)
            .listener(listener)
            .into(ivHotelImage)
    }

    private fun initPointMap(hotel: HotelDetailsModel) {
        val map = mvHotelMap.map
        val point = Point(hotel.lat!!, hotel.lon!!)
        map.move(
            CameraPosition(point, 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f),
            null
        )

        val mapObjects = map.mapObjects.addCollection()
        mapObjects.addPlacemark(point)
    }

    override fun error(error: Throwable?) {
        skeletonLayout.showOriginal()

        val alertDialogBuilder = AlertDialog.Builder(context!!)
        when (error) {
            else -> {
                val dialog = alertDialogBuilder
                    .setTitle(R.string.alert_default_title)
                    .setMessage(R.string.alert_default_description)
                    .setCancelable(false)
                    .setPositiveButton(
                        "Ок"
                    ) { dialogInterface, _ -> dialogInterface.dismiss() }
                    .create()

                dialog.apply {
                    setCanceledOnTouchOutside(false)
                    show()
                }
            }
        }
    }

    override fun back() = subjectBack
    override fun getHotelInfo() = subjectHotel


    companion object {
        const val HOTEL_ID = "HOTEL_ID"
    }
}