package com.example.vkapp.domain.usecases

import com.example.vkapp.domain.entity.FeedPost
import com.example.vkapp.domain.repository.NewsFeedRepository
import javax.inject.Inject

class ChangeLikeStatusUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(feedPost: FeedPost) {
        return repository.changeLikeStatus(feedPost)
    }
}