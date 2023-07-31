package com.example.vkapp.data.model.content.gettingPhotoDto

import com.google.gson.annotations.SerializedName

data class AttachmentDto(
    @SerializedName("photo") val photo: PhotoDto
)
