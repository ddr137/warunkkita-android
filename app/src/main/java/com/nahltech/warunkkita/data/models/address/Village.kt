package com.nahltech.warunkkita.data.models.address

import com.google.gson.annotations.SerializedName

data class Village (
    @SerializedName("id") var id: Long = 0,
    @SerializedName("village_name") var villageName: String = ""
)