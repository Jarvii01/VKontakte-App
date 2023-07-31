package com.example.vkapp.navigation.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.vkapp.domain.entity.FeedPost
import com.example.vkapp.navigation.Screen

fun NavGraphBuilder.homeScreenContent(
    newsFeedScreenContent: @Composable () -> Unit,
    commentScreenContent: @Composable (FeedPost) -> Unit,
//    feedPosts: FeedPost
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }

        composable(
            route = Screen.Comment.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_POST) {
                    type = FeedPost.NavigationType
                }
            )
        ) {
            val feedPost = it.arguments?.getParcelable<FeedPost>(Screen.KEY_FEED_POST)
                ?: throw RuntimeException("Args is null")
            commentScreenContent(feedPost)


        }

    }

}