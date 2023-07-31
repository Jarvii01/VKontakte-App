package com.example.vkapp.data.model

import com.example.vkapp.data.model.content.NewsFeedContentDto
import com.google.gson.annotations.SerializedName

data class NewsFeedResponseDto(
    @SerializedName("response") val newsFeedContent: NewsFeedContentDto
)
