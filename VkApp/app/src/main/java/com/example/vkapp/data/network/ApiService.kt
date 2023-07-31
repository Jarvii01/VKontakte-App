package com.example.vkapp.data.network

import com.example.vkapp.data.model.CommentsResponseDto
import com.example.vkapp.data.model.NewsFeedResponseDto
import com.example.vkapp.data.model.LikesCountResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadRecommendationsDto(
        @Query("access_token") token: String
    ):NewsFeedResponseDto


    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun nextLoadRecommendationsDto(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String
    ):NewsFeedResponseDto

    @GET("wall.getComments?v=5.131&extended=1&fields=photo_100")
    suspend fun getComments(
        @Query("access_token") token: String,
        @Query ("owner_id") ownerId: Long,
        @Query ("post_id") postId: Long
    ): CommentsResponseDto

    @GET("likes.delete?v=5.131&type=post")
    suspend fun deleteLikeDto(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    @GET("likes.add?v=5.131&type=post")
    suspend fun addLikeDto(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

}