package com.example.vkapp.data.model.content

import com.example.vkapp.data.model.content.postCommentContnetDto.PostCommentDto
import com.example.vkapp.data.model.content.postCommentContnetDto.ProfileDto
import com.google.gson.annotations.SerializedName

data class CommentsContentDto(
    @SerializedName("items") val comments: List<PostCommentDto>,
    @SerializedName ("profiles") val profiles: List <ProfileDto>
)
