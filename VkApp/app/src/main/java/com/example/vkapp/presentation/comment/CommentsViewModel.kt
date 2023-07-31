package com.example.vkapp.presentation.comment

import androidx.lifecycle.ViewModel
import com.example.vkapp.domain.entity.FeedPost
import com.example.vkapp.domain.usecases.GetCommentsUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class CommentsViewModel @Inject constructor(
    feedPost: FeedPost,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    val screenState = getCommentsUseCase(feedPost).map {
        CommentsScreenState.Comments(
            feedPost = feedPost,
            comment = it
        ) as CommentsScreenState
    }
        .onStart { emit(CommentsScreenState.Loading) }

}