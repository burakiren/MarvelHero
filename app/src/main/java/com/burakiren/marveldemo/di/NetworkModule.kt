package com.burakiren.marveldemo.di

import android.content.Context
import com.burakiren.data.apiService.ApiService
import com.burakiren.marveldemo.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.internal.and
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    private val PRIVATE_API_KEY_ARG = "hash"
    private val PUBLIC_API_KEY_ARG = "apikey"
    private val TS = "ts"
    private val TS_VALUE = "1"

    @Singleton
    @Provides
    fun providesOkHttpClient(
        context: Context
    ): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor { chain ->

                val calendar: Calendar = Calendar.getInstance()
                val timeInMillis = calendar.timeInMillis.toString()

                val stringToHash: String =
                    timeInMillis + BuildConfig.PRIVATE_API_KEY_VALUE + BuildConfig.PUBLIC_API_KEY_VALUE

                println("hash : " + md5Java(stringToHash))
                println("ts : " + calendar.getTimeInMillis())

                val defaultRequest = chain.request()
                val defaultHttpUrl = defaultRequest.url
                val httpUrl = defaultHttpUrl.newBuilder()
                    .addQueryParameter(PUBLIC_API_KEY_ARG, BuildConfig.PUBLIC_API_KEY_VALUE)
                    .addQueryParameter(
                        PRIVATE_API_KEY_ARG,
                        md5Java(stringToHash)
                    ).addQueryParameter(
                        "limit",
                        "5"
                    )
                    .addQueryParameter(TS, timeInMillis)
                    .build()

                val requestBuilder = defaultRequest.newBuilder().url(httpUrl)

                chain.proceed(requestBuilder.build())
            }
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    fun md5Java(message: String): String? {
        var digest: String? = null
        try {
            val md: MessageDigest = MessageDigest.getInstance("MD5")
            val hash: ByteArray = md.digest(message.toByteArray(charset("UTF-8")))
            //converting byte array to Hexadecimal String
            val sb = StringBuilder(2 * hash.size)
            for (b in hash) {
                sb.append(String.format("%02x", b and 0xff))
            }
            digest = sb.toString()
        } catch (ex: UnsupportedEncodingException) {
        } catch (ex: NoSuchAlgorithmException) {
        }
        return digest
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