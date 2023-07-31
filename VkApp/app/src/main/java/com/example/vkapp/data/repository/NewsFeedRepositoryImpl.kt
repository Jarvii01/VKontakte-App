package com.example.vkapp.data.repository

import android.util.Log
import com.example.vkapp.data.mapper.NewsFeedMapper
import com.example.vkapp.data.network.ApiService
import com.example.vkapp.domain.entity.AuthState
import com.example.vkapp.domain.entity.FeedPost
import com.example.vkapp.domain.entity.PostComment
import com.example.vkapp.domain.entity.StatisticItem
import com.example.vkapp.domain.entity.StatisticType
import com.example.vkapp.domain.repository.NewsFeedRepository
import com.example.vkapp.extentions.mergeWith
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: NewsFeedMapper,
    private val storage: VKPreferencesKeyValueStorage
) : NewsFeedRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    // Getting VK access
    private val token
        get() = VKAccessToken.restore(storage)

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    // Cached list of recommendations
    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()


    // Turn on authStateFlow
    private val checkAuthStateEvents = MutableSharedFlow<Unit>()

    // Authorisation check
    private val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            val currentToken = token
            val loggedIn = currentToken != null && currentToken.isValid
            val authState = if (loggedIn) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
            Log.d("MainViewModel", "Token: ${token?.accessToken}")
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    // Turn on loadedLikeFlow
    private var nextFrom: String? = null

    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedLikeFlow = MutableSharedFlow<List<FeedPost>>()

    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom

            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }

            val response = if (startFrom == null) {
                apiService.loadRecommendationsDto(getAccessToken())
            } else {
                apiService.nextLoadRecommendationsDto(getAccessToken(), startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToPost(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }.retry {
        delay(RETRY_DELAY_MILLIS)
        true
    }

    //Getting recommendations
    private val recommendations: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedLikeFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )


    //IMPLEMENTATION

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    override suspend fun checkAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow

    override fun getRecommendations(): StateFlow<List<FeedPost>> = recommendations

    override fun getComments(feedPost: FeedPost): Flow<List<PostComment>> = flow {
        val comments = apiService.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        emit(mapper.mapResponseToComments(comments))
    }.retry {
        delay(RETRY_DELAY_MILLIS)
        true
    }

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLikeDto(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            apiService.addLikeDto(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
        refreshedLikeFlow.emit(feedPosts)
    }

    companion object {

        const val RETRY_DELAY_MILLIS = 3000L

    }

}
