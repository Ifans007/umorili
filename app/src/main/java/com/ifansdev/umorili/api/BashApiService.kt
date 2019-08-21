package com.ifansdev.umorili.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ifansdev.umorili.BuildConfig
import com.ifansdev.umorili.api.dataclass.Quote
import com.ifansdev.umorili.api.dataclass.SourceOfQuotes
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient



interface BashApiService {

    @GET("get")
    fun searchQuotes(
            @Query("site") site: String,
            @Query("name") name: String,
            @Query("num") num: Int
    ): io.reactivex.Observable<List<Quote>>


    @GET("sources")
    fun searchSources(): io.reactivex.Observable<List<List<SourceOfQuotes>>>

    companion object Factory {

        private const val BASE_URL : String = "http://umorili.herokuapp.com/api/"

        fun create(): BashApiService {

            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
                    .build()

            val gson: Gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()

                    .client(okHttpClient)

                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BASE_URL)
                    .build()
            return retrofit.create(BashApiService::class.java)
        }
    }
}