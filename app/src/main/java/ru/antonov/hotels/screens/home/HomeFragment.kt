package ru.antonov.hotels.screens.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.home_fragment.*
import ru.antonov.hotels.R
import ru.antonov.hotels.data.HotelModel
import ru.antonov.hotels.utils.view.VerticalSpaceItemDecoration


class HomeFragment: MvpFragment<HomeView, HomePresenter>(), HomeView {
    private lateinit var hotelsAdapter: HotelsAdapter
    private var sortFieldPosition     : SortFieldsEnum = SortFieldsEnum.NONE
    private var sortDirection         : SortEnum = SortEnum.NONE
    private var subjectSort           : PublishSubject<SortModel> = PublishSubject.create<SortModel>()

    override fun createPresenter() = HomePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        if (savedInstanceState != null) {
            sortFieldPosition = SortFieldsEnum.getByValue(savedInstanceState.getInt(SORT_FIELD, SortFieldsEnum.NONE.ordinal))!!
            sortDirection     = SortEnum.getByValue(savedInstanceState.getInt(SORT_DIRECTION, SortEnum.NONE.ordinal))!!
        }

        hotelsAdapter = HotelsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.home_fragment, null, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rlProgressbar.visibility = View.VISIBLE

        btnSortAsc.isEnabled = false

        sFilterVariants.setSelection(sortFieldPosition.ordinal)

        sFilterVariants.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                btnSortAsc.isEnabled = position > 0
                sortFieldPosition    = SortFieldsEnum.getByValue(position)!!
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        btnSortAsc.setOnClickListener { btn ->
            sortDirection = when(sortDirection) {
                SortEnum.ASC  -> {
                    SortEnum.DESC
                }
                SortEnum.DESC -> {
                    SortEnum.ASC
                }
                else -> SortEnum.ASC
            }

            when (sortDirection) {
                SortEnum.ASC  -> btn.rotation = 0f
                SortEnum.DESC -> btn.rotation = 180f
                else          -> btn.rotation = 0f
            }

            subjectSort.onNext(SortModel(sortFieldPosition, sortDirection, hotelsAdapter.getDataset()))
        }

        rvHotels.addItemDecoration(VerticalSpaceItemDecoration(20))

        rvHotels.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter       = hotelsAdapter
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SORT_FIELD, sortFieldPosition.ordinal)
        outState.putInt(SORT_DIRECTION, sortDirection.ordinal)
    }

    override fun loadHotels(hotels: ArrayList<HotelModel>) {
        if (hotels.isNullOrEmpty()) {
            rlProgressbar.visibility = View.VISIBLE
            return
        }

        rlProgressbar.visibility = View.GONE
        hotelsAdapter.setDataset(hotels)
        hotelsAdapter.notifyDataSetChanged()
    }

    override fun error(error: Throwable?) {
        rlProgressbar.visibility = View.GONE

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

    override fun hotelClick() = hotelsAdapter.subjectItemClick
    override fun sortHotels() = subjectSort

    companion object {
        val TAG = HomeFragment::class.java.simpleName
        const val SORT_FIELD     = "SORT_FIELD"
        const val SORT_DIRECTION = "SORT_DIRECTION"
    }
}