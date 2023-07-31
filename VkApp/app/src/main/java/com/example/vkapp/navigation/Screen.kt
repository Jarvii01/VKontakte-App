package com.example.vkapp.navigation

import android.net.Uri
import com.example.vkapp.domain.entity.FeedPost
import com.google.gson.Gson

sealed class Screen(
    val route: String
){

    object Home: Screen(ROUTE_HOME)

    object Favourite: Screen(ROUTE_FAVOURITE)

    object Profile: Screen(ROUTE_PROFILE)

    object NewsFeed: Screen(ROUTE_NEWS_FEED)

    object Comment: Screen(ROUTE_COMMENTS){
        private const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(feedPost: FeedPost): String{
            val feedPostJson = Gson().toJson(feedPost)
            return "$ROUTE_FOR_ARGS/${feedPostJson.encode()}"
        }

    }


    companion object{
        const val KEY_FEED_POST = "feed_post"


        private const val ROUTE_HOME = "home"
        private const val ROUTE_FAVOURITE = "favourite"
        private const val ROUTE_PROFILE = "profile"
        private const val ROUTE_NEWS_FEED = "news_feed"
        private const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST}"
    }
    fun String.encode():String{
        return Uri.encode(this)
    }
}

