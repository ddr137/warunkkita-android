package com.nahltech.warunkkita.data.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("shopping_count") var shoppingCount: String? = null,
    @SerializedName("seller") var seller: String? = null,
    @SerializedName("order_count") var orderCount: String? = null,
    @SerializedName("free_ongkir") var freeOngkir: String? = null,
    @SerializedName("rating") var rating: String? = null,
    @SerializedName("api_token") var api_token: String? = null,
    @SerializedName("registered") var registered: String? = null
)