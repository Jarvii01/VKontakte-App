package com.example.vkapp.domain.usecases

import com.example.vkapp.domain.repository.NewsFeedRepository
import javax.inject.Inject

class LoadNextDataUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(){
        repository.loadNextData()
    }
}