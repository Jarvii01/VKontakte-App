package com.example.vkapp.domain.usecases

import com.example.vkapp.domain.entity.AuthState
import com.example.vkapp.domain.entity.FeedPost
import com.example.vkapp.domain.entity.PostComment
import com.example.vkapp.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAuthStateFlowUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    operator fun invoke() : StateFlow<AuthState> {
        return repository.getAuthStateFlow()
    }
}