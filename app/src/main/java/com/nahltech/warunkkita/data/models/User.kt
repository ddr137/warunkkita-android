package com.nahltech.warunkkita.data.models

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id") var id : String? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("api_token") var api_token : String? = null
)