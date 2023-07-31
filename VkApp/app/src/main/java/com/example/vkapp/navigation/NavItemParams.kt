package com.example.vkapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItemParams(
    val screen: Screen,
    val icon: ImageVector,
    val titleResId: String
    ){

    object Home: NavItemParams(
        screen = Screen.Home,
        icon = Icons.Default.Home,
        titleResId = "Главная"
    )

    object Favourite: NavItemParams(
        screen = Screen.Favourite,
        icon = Icons.Outlined.Favorite,
        titleResId = "Избранное"
    )

    object Profile: NavItemParams(
        screen = Screen.Profile,
        icon = Icons.Outlined.Person,
        titleResId = "Профиль"
    )



}
