package com.example.vkapp.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vkapp.navigation.NavItemParams
import com.example.vkapp.navigation.navGraph.AppNavGraph
import com.example.vkapp.navigation.rememberNavController
import com.example.vkapp.presentation.ViewModelFactory
import com.example.vkapp.presentation.comment.CommentsScreen
import com.example.vkapp.presentation.news.NewsFeedScreen

@Composable
fun MainScreen(viewModelFactory: ViewModelFactory) {


    val navigationState = rememberNavController()

    Scaffold(
        bottomBar = {

            val navBackStackEntry = navigationState.navHostController.currentBackStackEntryAsState()

            BottomNavigation {

                val items = listOf(
                    NavItemParams.Home,
                    NavItemParams.Favourite,
                    NavItemParams.Profile
                )


                items.forEach { items ->

                    val selected = navBackStackEntry.value?.destination?.hierarchy?.any() {
                        it.route == items.screen.route
                    } ?: false


                    BottomNavigationItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navScreenParams(items.screen.route)
                            }
                        },
                        icon = {
                            Icon(imageVector = items.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = items.titleResId, color = MaterialTheme.colors.secondary)

                        }
                    )
                }


            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                NewsFeedScreen(
                    paddingValues = paddingValues,
                    onCommentClickListener = {
                        navigationState.navigateToComments(it)
                    },
                    viewModelFactory = viewModelFactory
                )
            },
            commentScreenContent = { feedPost ->
                CommentsScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    feedPost = feedPost


                )
            },
            favouriteScreenContent = { TextCounter() },
            profileScreenContent = { TextCounter() },

            )
    }
}


@Composable
private fun TextCounter() {
    var count by rememberSaveable {
        mutableStateOf(0)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.clickable { count++ },
            text = "Возможно, когда-то, здесь что-то будет :)",
            color = Color.Gray,
            fontSize = 20.sp
        )
    }

}