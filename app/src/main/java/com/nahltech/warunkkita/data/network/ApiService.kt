package com.nahltech.warunkkita.data.network

import com.nahltech.warunkkita.data.models.Banner
import com.nahltech.warunkkita.data.models.Register
import com.nahltech.warunkkita.data.models.ResponseImageUploader
import com.nahltech.warunkkita.data.models.User
import com.nahltech.warunkkita.data.models.address.City
import com.nahltech.warunkkita.data.models.address.District
import com.nahltech.warunkkita.data.models.address.Province
import com.nahltech.warunkkita.data.models.address.Village
import com.nahltech.warunkkita.utils.WrappedListResponse
import com.nahltech.warunkkita.utils.WrappedResponse
import io.reactivex.Observable
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

    /** Change Image **/
    @Multipart
    @POST("api/users/{id}/change-image")
    fun changeImage(
        //@Header("Authorization") token : String,
        @Path("id") id : String,
        @Part image: MultipartBody.Part
    ): Call<WrappedResponse<ResponseImageUploader>>

    /** Edit Account **/
    @FormUrlEncoded
    @POST("api/users/{id}")
    fun editAccount(
        @Header("Authorization") token : String,
        @Path("id") id : String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String
    ): Call<WrappedResponse<User>>

    /** Get Province, district, village, city **/
    @GET("api/get-address/provinces")
    fun provinces(): Observable<WrappedListResponse<Province>>

    @GET("api/get-address/cities/{id}")
    fun cities(@Path("id") id: Long): Observable<WrappedListResponse<City>>

    @GET("api/get-address/districts/{id}")
    fun districts(@Path("id") id: Long): Observable<WrappedListResponse<District>>

    @GET("api/get-address/villages/{id}")
    fun villages(@Path("id") id: Long): Observable<WrappedListResponse<Village>>

    /** Edit Address **/
    @FormUrlEncoded
    @POST("api/users/{id}/change-address")
    fun editAddress(
        @Header("Authorization") token : String,
        @Path("id") id : String,
        @Field("province_name") provinceId: String,
        @Field("city_name") cityId: String,
        @Field("districts_name") districtId: String,
        @Field("village_name") villageId: String,
        @Field("address") completeAddress: String,
        @Field("postal_code") postalCode: String
    ): Call<WrappedResponse<User>>

    /** Change Password **/
    @FormUrlEncoded
    @POST("api/users/{id}/change-password")
    fun changePassword(
        @Header("Authorization") token : String,
        @Path("id") id : String,
        @Field("old_password") oldPassword: String,
        @Field("new_password") newPassword: String,
        @Field("confirm_password") confirmPassword: String
    ): Call<WrappedResponse<User>>

    /** Account Bank **/
    @FormUrlEncoded
    @POST("api/users/{id}/change-bank")
    fun accountBank(
        @Header("Authorization") token : String,
        @Path("id") id : String,
        @Field("bank_name") bankName: String,
        @Field("account_number") accountNumber: String,
        @Field("owner_name") ownerName: String,
        @Field("branch") branch: String
    ): Call<WrappedResponse<User>>


}