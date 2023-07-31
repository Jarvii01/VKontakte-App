package com.example.vkapp.presentation.news

import com.example.vkapp.domain.entity.FeedPost

sealed class NewsFeedScreenState {

    object Loading : NewsFeedScreenState()

    object Initial : NewsFeedScreenState()

    data class Posts(
        val posts: List<FeedPost>,
        val nextDataIsLoading: Boolean = false
    ) : NewsFeedScreenState()


}