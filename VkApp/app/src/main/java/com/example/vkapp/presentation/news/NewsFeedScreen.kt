package com.example.vkapp.presentation.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vkapp.domain.entity.FeedPost
import com.example.vkapp.presentation.ViewModelFactory
import com.example.vkapp.ui.theme.DarkBlue

@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (FeedPost) -> Unit,
    viewModelFactory: ViewModelFactory
) {
    val viewModel: NewsScreenViewModel = viewModel(factory = viewModelFactory)

    val screenState = viewModel.screenState.collectAsState(NewsFeedScreenState.Initial)

    when (val currentState = screenState.value) {
        is NewsFeedScreenState.Posts -> {
            FeedPosts(
                paddingValues = paddingValues,
                posts = currentState.posts,
                nextDataLoading = currentState.nextDataIsLoading,
                viewModel = viewModel,
                onCommentClickListener = onCommentClickListener
            )
        }

        is NewsFeedScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = DarkBlue)
            }


        }

        is NewsFeedScreenState.Initial -> {}


        else -> {}
    }

}

@Composable
fun FeedPosts(
    paddingValues: PaddingValues,
    posts: List<FeedPost>,
    nextDataLoading: Boolean,
    viewModel: NewsScreenViewModel = viewModel(),
    onCommentClickListener: (FeedPost) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        )
    ) {
        items(items = posts, key = { it.id }) { feedPost ->

            PostCard(
                feedPost = feedPost,
                onCommentClickListener = {
                    onCommentClickListener(feedPost)
                },
                onLikeClickListener = { _ ->
                    viewModel.changeLikeStatus(feedPost)
                }
            )
        }
        item {
            if (nextDataLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = DarkBlue)
                }
            } else {
                SideEffect {
                    viewModel.nextLoadRecommendations()
                }
            }
        }
    }
}