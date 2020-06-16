package com.nahltech.warunkkita.data.network

import com.nahltech.warunkkita.data.models.Banner
import com.nahltech.warunkkita.data.models.Register
import com.nahltech.warunkkita.data.models.ResponseImageUploader
import com.nahltech.warunkkita.data.models.User
import com.nahltech.warunkkita.utils.WrappedResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    /** Image Slider **/
    @GET("api/banner-sliders")
    fun getBannerSlider(): Call<List<Banner>>

    /** Auth **/
    @FormUrlEncoded
    @POST("api/auth/login")
    fun login(
        @Field("email_phone") emailPhone: String,
        @Field("password") password: String
    ): Call<WrappedResponse<User>>

    @FormUrlEncoded
    @POST("api/auth/register")
    fun register(
        @Field("email_phone") emailPhone: String,
        @Field("password") password: String
    ): Call<WrappedResponse<User>>

    @FormUrlEncoded
    @POST("api/auth/register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("ulangi_password") retryPassword: String
    ): Call<WrappedResponse<Register>>

    /** Get Profile **/
    @GET("api/users/profile")
    fun getProfile(
        @Header("Authorization") token: String
    ): Call<WrappedResponse<User>>

    @Multipart
    @POST("api/users/{id}/change-image")
    fun changeImage(
        //@Header("Authorization") token : String,
        @Path("id") id : String,
        @Part image: MultipartBody.Part
    ): Call<WrappedResponse<ResponseImageUploader>>

    @FormUrlEncoded
    @POST("api/users/{id}")
    fun editAccount(
        @Header("Authorization") token : String,
        @Path("id") id : String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("gender") gender: String
    ): Call<WrappedResponse<User>>

}