package com.example.vkapp.data.model.content.newsFeedContentDto

import com.example.vkapp.data.model.content.gettingPhotoDto.AttachmentDto
import com.example.vkapp.data.model.content.postStatisticsDto.CommentsDto
import com.example.vkapp.data.model.content.postStatisticsDto.LikesDto
import com.example.vkapp.data.model.content.postStatisticsDto.RepostsDto
import com.example.vkapp.data.model.content.postStatisticsDto.ViewsDto
import com.google.gson.annotations.SerializedName

data class PostDto(

    @SerializedName("id") val id: Long,
    @SerializedName("source_id") val communityId: Long,
    @SerializedName("date") val date: Long,
    @SerializedName("text") val contentText: String,
    @SerializedName("likes") val likes: LikesDto,
    @SerializedName("comments") val comments: CommentsDto,
    @SerializedName("views") val views: ViewsDto,
    @SerializedName("reposts") val reposts: RepostsDto,
    @SerializedName("attachments") val attachments: List<AttachmentDto>?

)
