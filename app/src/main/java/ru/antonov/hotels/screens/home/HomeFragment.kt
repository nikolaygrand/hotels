package ru.antonov.hotels.screens.home


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.home_fragment.*
import ru.antonov.hotels.R
import ru.antonov.hotels.data.HotelModel
import ru.antonov.hotels.mvp.BaseFragment
import ru.antonov.hotels.utils.view.VerticalSpaceItemDecoration


class HomeFragment : BaseFragment<HomeView, HomePresenter>(), HomeView {
    private lateinit var hotelsAdapter: HotelsAdapter
    private var sortFieldPosition: SortFieldsEnum = SortFieldsEnum.NONE
    private var sortDirection: SortEnum = SortEnum.NONE
    private var subjectSort = PublishSubject.create<SortModel>()
    private var subjectRefresh = PublishSubject.create<Unit>()
    private lateinit var skeleton: Skeleton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        if (savedInstanceState != null) {
            sortFieldPosition = SortFieldsEnum.getByValue(
                savedInstanceState.getInt(
                    SORT_FIELD,
                    SortFieldsEnum.NONE.ordinal
                )
            )!!
            sortDirection = SortEnum.getByValue(
                savedInstanceState.getInt(
                    SORT_DIRECTION,
                    SortEnum.NONE.ordinal
                )
            )!!
        }

        hotelsAdapter = HotelsAdapter()
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.home_fragment, null, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                sortFieldPosition = SortFieldsEnum.getByValue(position)!!
                makeSortHotels()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        btnSortAsc.setOnClickListener { btn ->
            sortDirection = when (sortDirection) {
                SortEnum.ASC -> {
                    SortEnum.DESC
                }
                SortEnum.DESC -> {
                    SortEnum.ASC
                }
                else -> SortEnum.ASC
            }

            when (sortDirection) {
                SortEnum.ASC -> btn.rotation = 0f
                SortEnum.DESC -> btn.rotation = 180f
                else -> btn.rotation = 0f
            }

            makeSortHotels()
        }

        rvHotels.addItemDecoration(VerticalSpaceItemDecoration(20))

        with(rvHotels) {
            adapter = hotelsAdapter
        }

        srlHotels.setOnRefreshListener {
            srlHotels.isRefreshing = false
            subjectRefresh.onNext(Unit)
        }

        skeleton = rvHotels.applySkeleton(R.layout.hotel_item, 3)
    }

    private fun makeSortHotels() {
        subjectSort.onNext(SortModel(sortFieldPosition, sortDirection, hotelsAdapter.getDataset()))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SORT_FIELD, sortFieldPosition.ordinal)
        outState.putInt(SORT_DIRECTION, sortDirection.ordinal)
    }

    override fun loadHotels(hotels: ArrayList<HotelModel>) {
        if (hotels.isNullOrEmpty()) {
            skeleton.showSkeleton()
            return
        }

        skeleton.showOriginal()
        hotelsAdapter.notifyDataSetChanged()
    }

    override fun error(error: Throwable?) {
        skeleton.showOriginal()

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
    override fun refresh(): Observable<Unit> = subjectRefresh

    companion object {
        val TAG = HomeFragment::class.java.simpleName
        const val SORT_FIELD = "SORT_FIELD"
        const val SORT_DIRECTION = "SORT_DIRECTION"
    }

    override fun createPresenter() = HomePresenter()
}