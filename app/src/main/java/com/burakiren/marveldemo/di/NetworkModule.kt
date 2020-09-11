package com.burakiren.marveldemo.di

import android.content.Context
import com.burakiren.data.apiService.ApiService
import com.burakiren.marveldemo.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(
        context: Context
    ): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpCacheDirectory = File(context.cacheDir, "cache_file")
        val cache = Cache(httpCacheDirectory, 20 * 1024 * 1024)

        return OkHttpClient.Builder()
            .addNetworkInterceptor(CacheInterceptor(context))
            .addInterceptor(MarvelHashInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .cache(cache)
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.MARVEL_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMarvelApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}