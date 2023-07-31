package com.example.vkapp.data.model.content.postStatisticsDto

import com.google.gson.annotations.SerializedName

data class LikesDto(
    @SerializedName("count") val count: Int,
    @SerializedName ("user_likes") val userLikes: Int
)
