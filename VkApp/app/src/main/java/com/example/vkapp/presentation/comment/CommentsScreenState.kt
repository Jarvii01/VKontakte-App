package com.example.vkapp.presentation.comment

import com.example.vkapp.domain.entity.FeedPost
import com.example.vkapp.domain.entity.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    object Loading: CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comment: List<PostComment>
    ) : CommentsScreenState()


}


