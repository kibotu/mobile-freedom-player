package com.exozet.freedomplayer

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

internal interface ExozetService {

//    @Headers("Authorization: Bearer")
    @GET("{file}")
    fun interiorJson(@Path("file", encoded = true) file: String): Observable<Interior>

//    @Headers("Authorization: Bearer")
    @GET("{file}")
    fun exteriorJson(@Path("file", encoded = true) file: String): Observable<Exterior>
}