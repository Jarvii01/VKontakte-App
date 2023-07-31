package com.example.vkapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vkapp.domain.entity.FeedPost

class NavigationState(
    val navHostController: NavHostController
) {

    fun navScreenParams(route: String) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToComments(feedPost: FeedPost){
        navHostController.navigate(Screen.Comment.getRouteWithArgs(feedPost))
    }
}


@Composable
fun rememberNavController(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }

}