package com.nahltech.warunkkita.data.models

import com.google.gson.annotations.SerializedName

data class Register (
    @SerializedName("name") var name : String? = null,
    @SerializedName("email") var email : String? = null,
    @SerializedName("phone") var phone : String? = null,
    @SerializedName("password") var password : String? = null,
    @SerializedName("ulangi_password") var retryPassword : String? = null
)