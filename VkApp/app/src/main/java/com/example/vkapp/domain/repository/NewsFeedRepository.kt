package com.example.vkapp.domain.repository

import com.example.vkapp.domain.entity.AuthState
import com.example.vkapp.domain.entity.FeedPost
import com.example.vkapp.domain.entity.PostComment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    fun getRecommendations(): StateFlow<List<FeedPost>>

    fun getComments(feedPost: FeedPost): Flow<List<PostComment>>

    suspend fun loadNextData()

    suspend fun checkAuthState()

    suspend fun changeLikeStatus(feedPost: FeedPost)

}
