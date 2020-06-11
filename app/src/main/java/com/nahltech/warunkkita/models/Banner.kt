package com.nahltech.warunkkita.models

import com.google.gson.annotations.SerializedName

class Banner (
    @field:SerializedName("id") var id: String,
    @field:SerializedName("image") var image: String,
    @field:SerializedName("slug") var slug: String,
    @field:SerializedName("title") var title: String,
    @field:SerializedName("created_at") var createdAt: String,
    @field:SerializedName("type") var type: String
)