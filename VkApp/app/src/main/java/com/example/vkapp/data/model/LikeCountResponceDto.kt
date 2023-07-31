package com.example.vkapp.data.model

import com.example.vkapp.data.model.content.postStatisticsDto.LikesCountDto
import com.google.gson.annotations.SerializedName

data class LikesCountResponseDto(
    @SerializedName("response") val likes: LikesCountDto
)

