package com.nahltech.warunkkita.data.models.address

import com.google.gson.annotations.SerializedName

data class District (
    @SerializedName("district_id") var id: Long = 0,
    @SerializedName("district_name") var districtName: String = ""
)