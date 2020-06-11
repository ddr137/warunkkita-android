package com.nahltech.warunkkita.network

import com.nahltech.warunkkita.models.Banner
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    /** Image Slider **/
    @GET("api/banner-sliders")
    fun getBannerSlider(): Call<List<Banner>>
}