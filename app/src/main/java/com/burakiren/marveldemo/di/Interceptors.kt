package com.burakiren.marveldemo.di

import android.content.Context
import com.burakiren.marveldemo.BuildConfig
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.and
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.InetAddress
import java.net.UnknownHostException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


var cacheInterceptor: Interceptor = object : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = if (isOnline()) {
            request.newBuilder().header("Cache-Control", String.format("max-age=%d", 50000)).build()
        } else {
            request.newBuilder()
                .header("Cache-Control", String.format("max-age=%d", 50000))

                .build()
        }
        return chain.proceed(request)
    }
}

class MarvelHashInterceptor : Interceptor {

    private val PRIVATE_API_KEY_ARG = "hash"
    private val PUBLIC_API_KEY_ARG = "apikey"
    private val TS = "ts"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
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

        return chain.proceed(requestBuilder.build())
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
}

class CacheInterceptor(var context: Context) : Interceptor {
    companion object {
        const val TAG = "CacheInterceptor"

        const val MAX_AGE_OFFLINE = 60 * 60 * 24 * 28 // 28 days

        const val MAX_AGE = 60 * 60 * 24 // 1 days
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        return if (isOnline()) {
            val response = chain.proceed(request)
            val cacheControl = request.cacheControl.toString()
            response.newBuilder()
                .header("Cache-Control", "public, max-age=" + MAX_AGE)
                .build()
        } else {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            val response = chain.proceed(request)
            response.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=" + MAX_AGE_OFFLINE)
                .build()
        }
    }
}

fun isOnline(): Boolean {
    return try {
        val address: InetAddress = InetAddress.getByName("www.google.com")
        println("Network Connection : " + !address.equals(""))
        !address.equals("")
    } catch (e: UnknownHostException) {
        println("Network Connection : " + false)
        false
    }
}