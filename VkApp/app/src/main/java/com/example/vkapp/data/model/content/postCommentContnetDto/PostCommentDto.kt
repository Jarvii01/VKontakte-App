package com.example.vkapp.data.model.content.postCommentContnetDto

import com.google.gson.annotations.SerializedName

data class PostCommentDto(
    @SerializedName("id") val id: Long,
    @SerializedName("from_id") val authorId: Long,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long
)
