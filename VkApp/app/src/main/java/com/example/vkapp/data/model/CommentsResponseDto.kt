package com.example.vkapp.data.model

import com.example.vkapp.data.model.content.CommentsContentDto
import com.google.gson.annotations.SerializedName

data class CommentsResponseDto(
    @SerializedName("response") val commentsContent: CommentsContentDto
)