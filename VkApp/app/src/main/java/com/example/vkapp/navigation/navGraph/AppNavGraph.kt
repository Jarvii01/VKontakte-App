package com.example.vkapp.navigation.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vkapp.domain.entity.FeedPost
import com.example.vkapp.navigation.Screen

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    profileScreenContent: @Composable () -> Unit,
    favouriteScreenContent: @Composable () -> Unit,
    newsFeedScreenContent: @Composable () -> Unit,
    commentScreenContent: @Composable (FeedPost) -> Unit
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ){

        homeScreenContent(
            newsFeedScreenContent = newsFeedScreenContent,
            commentScreenContent = commentScreenContent
        )

        composable(Screen.Favourite.route){
            favouriteScreenContent()
        }

        composable(Screen.Profile.route){
            profileScreenContent()
        }


    }
}