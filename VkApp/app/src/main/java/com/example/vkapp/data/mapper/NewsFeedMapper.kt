package com.example.vkapp.data.mapper

import com.example.vkapp.data.model.CommentsResponseDto
import com.example.vkapp.data.model.NewsFeedResponseDto
import com.example.vkapp.domain.entity.FeedPost
import com.example.vkapp.domain.entity.PostComment
import com.example.vkapp.domain.entity.StatisticItem
import com.example.vkapp.domain.entity.StatisticType
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.absoluteValue

class NewsFeedMapper @Inject constructor() {

    fun mapResponseToPost(response: NewsFeedResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()

        val posts = response.newsFeedContent.posts
        val groups = response.newsFeedContent.groups

        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: break
            val feedPost = FeedPost(
                id = post.id,
                communityId = post.communityId,
                publicationDate = mapTimestampToDate(post.date),
                communityName = group.communityName,
                communityImageUrl = group.communityImageUrl,
                contentText = post.contentText,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(StatisticType.LIKES, post.likes.count),
                    StatisticItem(StatisticType.COMMENTS, post.comments.count),
                    StatisticItem(StatisticType.VIEWS, post.views.count),
                    StatisticItem(StatisticType.SHARES, post.reposts.count)
                ),
                isLiked = post.likes.userLikes > 0

            )
            result.add(feedPost)

        }
        return result
    }

    fun mapResponseToComments(response: CommentsResponseDto): List<PostComment> {
        val result = mutableListOf<PostComment>()
        val comments = response.commentsContent.comments
        val profiles = response.commentsContent.profiles
        for (comment in comments) {
            if (comment.text.isBlank()) continue
            val author = profiles.firstOrNull { it.id == comment.authorId } ?: continue
            val postComment = PostComment(
                id = comment.id,
                authorName = "${author.firstName} ${author.lastName}",
                authorAvatarUrl = author.avatarUrl,
                commentText = comment.text,
                publicationDate = mapTimestampToDate(comment.date)
            )

            result.add(postComment)
        }
        return result
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }


}
