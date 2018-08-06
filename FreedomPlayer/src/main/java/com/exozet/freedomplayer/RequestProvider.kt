package com.exozet.freedomplayer

import android.app.Application
import android.net.Uri
import android.util.Log
import com.exozet.freedomplayer.SslUtils.getSslContextForCertificateFile
import com.exozet.freedomplayer.SslUtils.getTrustAllHostsSSLSocketFactory
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit


internal object RequestProvider {

    private val TAG by lazy { this::class.java.simpleName }

    private val dangerouslyTrustingAllHosts = false
    private val authToken: String? = null // "6320447e-3e22-4d4c-b29a-89216cd82e4f"
    private val enableLogging = true
    private val application: WeakReference<Application>? = null
    private val certificateFileName = "cert.pem"

    internal fun exteriorJson(uri: Uri): Observable<Exterior> = createService(uri).exteriorJson(uri.path)

    internal fun interiorJson(uri: Uri): Observable<Interior> = createService(uri).interiorJson(uri.path)

    private fun createService(uri: Uri): ExozetService {
        return Retrofit.Builder()
                .baseUrl(uri.scheme + "://" + uri.host)
                .client(createOkHttpClient(authToken?.let { hashMapOf(Pair("Authorization", "Bearer $it")) }).build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ExozetService::class.java)
    }

    @JvmStatic
    private fun createOkHttpClient(additionalHeader: Map<String, String>? = null, logging: Boolean = true): OkHttpClient.Builder {
        val client = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .apply {
                    if (logging)
                        addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { log(it) })
                                .apply {
                                    level = if (enableLogging)
                                        HttpLoggingInterceptor.Level.BODY
                                    else
                                        HttpLoggingInterceptor.Level.NONE
                                })
                }
                .addNetworkInterceptor {
                    it.proceed(it.request()
                            .newBuilder()
                            .apply {
                                additionalHeader?.let { headers ->
                                    for ((key, value) in headers) {
                                        log("header -> $key : $value")
                                        header(key, value)
                                    }
                                }
                            }
                            .build())
                }

        application?.get()?.let {
            client.sslSocketFactory(getSslContextForCertificateFile(it, certificateFileName).socketFactory)
        }

        if (dangerouslyTrustingAllHosts)
            getTrustAllHostsSSLSocketFactory()?.let {
                client.sslSocketFactory(it)
            }

        return client
    }

    private fun log(message: String) = Log.v(TAG, message)
}