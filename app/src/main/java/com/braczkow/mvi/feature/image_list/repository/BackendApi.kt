package com.braczkow.mvi.feature.image_list.repository

import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import timber.log.Timber

interface BackendApi {

    @GET("list")
    fun getImagesList(): Single<ListResp>

    companion object {
        fun create(): BackendApi {
            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor(
                    HttpLoggingInterceptor(
                            { message -> Timber.i(message) }
                    ).setLevel(HttpLoggingInterceptor.Level.BODY))

            val rxAdapterFactory = RxJava3CallAdapterFactory.create()

            return Retrofit.Builder()
                    .baseUrl("https://picsum.photos/v2/")
                    .client(httpClient.build())
                    .addCallAdapterFactory(rxAdapterFactory)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BackendApi::class.java)
        }
    }

}

