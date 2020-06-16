package com.nahltech.warunkkita.data.models.address

import com.google.gson.annotations.SerializedName

data class Province(
    @SerializedName("province_id") var id: Long = 0,
    @SerializedName("province_name") var provinceName: String = ""
)