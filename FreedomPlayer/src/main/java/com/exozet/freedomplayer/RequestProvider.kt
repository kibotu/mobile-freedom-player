package com.exozet.freedomplayer

import android.net.Uri
import android.util.Log
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


internal object RequestProvider {

    private val TAG by lazy { this::class.java.simpleName }

    private val authToken: String? = null // "6320447e-3e22-4d4c-b29a-89216cd82e4f"

    private val enableLogging = true

    internal fun exteriorJson(uri: Uri): Observable<Exterior> = createWebService(uri).exteriorJson(uri.path!!)

    internal fun interiorJson(uri: Uri): Observable<Interior> = createWebService(uri).interiorJson(uri.path!!)

    private fun createWebService(uri: Uri) = createWebService<ExozetService>(createOkHttpClient(AuthorizationInterceptor { authToken }), "${uri.scheme}://${uri.host}")

    private fun createOkHttpClient(authorizationInterceptor: AuthorizationInterceptor) = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(ContentTypeInterceptor())
        .addInterceptor(authorizationInterceptor)
        .addInterceptor(createHttpLoggingInterceptor { enableLogging })
        .build()

    private inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String) = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(T::class.java)

    internal class ContentTypeInterceptor(private val contentType: String = "application/json") : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(with(chain.request().newBuilder()) { header("Content-Type", contentType).build() })
    }

    internal class AuthorizationInterceptor(private val tokenProvider: () -> String?) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()

            // add jwt token
            if (request.header("Authorization") == "Bearer") {
                request = request.newBuilder().removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer ${tokenProvider()}")
                    .build()
            }

            return chain.proceed(request)
        }
    }

    private fun createHttpLoggingInterceptor(enableLogging: () -> Boolean): HttpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            if (enableLogging())
                Log.v(TAG, message)
        }
    }).apply {
        level = if (enableLogging())
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }
}