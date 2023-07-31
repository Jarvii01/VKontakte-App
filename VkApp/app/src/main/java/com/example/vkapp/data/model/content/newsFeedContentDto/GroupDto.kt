package com.example.vkapp.data.model.content.newsFeedContentDto

import com.google.gson.annotations.SerializedName

data class GroupDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val communityName: String,
    @SerializedName("photo_200") val communityImageUrl: String
)
