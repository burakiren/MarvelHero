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

        val httpCacheDirectory = File(context.cacheDir, "offlineCache")
        val cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)

        return OkHttpClient.Builder()
            .addNetworkInterceptor(MarvelHashInterceptor())
            .addNetworkInterceptor { chain ->
                var request = chain.request()
                request = if (isOnline())
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                chain.proceed(request)
            }
            //.addInterceptor(provideOfflineCacheInterceptor())
            //.addNetworkInterceptor(provideCacheInterceptor())
            .cache(cache)
            //.addInterceptor(httpLoggingInterceptor)
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