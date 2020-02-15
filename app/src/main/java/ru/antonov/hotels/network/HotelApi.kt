package ru.antonov.hotels.network

import android.content.Context
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.antonov.hotels.BuildConfig

class HotelApi constructor(context: Context) {
    private var mClient: IHotelApi

    private var interceptor = InterceptorApi()
    private val logInterceptor = HttpLoggingInterceptor()

    init {
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient().newBuilder()
            .addInterceptor(logInterceptor)
            .addInterceptor(interceptor)
            .connectionSpecs(
                listOf(
                    ConnectionSpec.MODERN_TLS,
                    ConnectionSpec.COMPATIBLE_TLS,
                    ConnectionSpec.CLEARTEXT
                )
            )
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        mClient = retrofit.create(IHotelApi::class.java)
    }

    fun getHotelsApi(): IHotelApi {
        return mClient
    }
}
