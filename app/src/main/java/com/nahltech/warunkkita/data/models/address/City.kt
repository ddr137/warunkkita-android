package com.nahltech.warunkkita.data.models.address

import com.google.gson.annotations.SerializedName

data class City (
    @SerializedName("city_id") var id: Long = 0,
    @SerializedName("city_name") var cityName: String = ""
)