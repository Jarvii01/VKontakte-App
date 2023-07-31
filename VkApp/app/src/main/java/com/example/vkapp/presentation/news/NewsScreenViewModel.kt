package com.example.vkapp.presentation.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkapp.domain.entity.FeedPost
import com.example.vkapp.domain.usecases.ChangeLikeStatusUseCase
import com.example.vkapp.domain.usecases.GetRecommendationsUseCase
import com.example.vkapp.domain.usecases.LoadNextDataUseCase
import com.example.vkapp.extentions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsScreenViewModel @Inject constructor(
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val loadNextDataUseCase: LoadNextDataUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("NewsScreenViewModel", "Exception caught by exception handler")
    }


    private val getRecommendationsFlow = getRecommendationsUseCase()


    // Hot flow
    private val loadNextDataFlow = MutableSharedFlow<NewsFeedScreenState>()
    val screenState = getRecommendationsFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.Posts(posts = it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(this.loadNextDataFlow)


    fun nextLoadRecommendations() {
        viewModelScope.launch {
            this@NewsScreenViewModel.loadNextDataFlow.emit(
                NewsFeedScreenState.Posts(
                    posts = getRecommendationsFlow.value,
                    nextDataIsLoading = true
                )
            )
            loadNextDataUseCase()
        }

    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUseCase(feedPost)
        }
    }

}