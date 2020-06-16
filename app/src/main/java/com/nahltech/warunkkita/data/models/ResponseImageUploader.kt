package com.nahltech.warunkkita.data.models

import com.google.gson.annotations.SerializedName

data class ResponseImageUploader(
    @SerializedName("image") var image: String? = null
)