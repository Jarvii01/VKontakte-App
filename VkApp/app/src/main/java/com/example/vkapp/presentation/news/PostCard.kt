package com.example.vkapp.presentation.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vkapp.R
import com.example.vkapp.domain.entity.FeedPost
import com.example.vkapp.domain.entity.StatisticItem
import com.example.vkapp.domain.entity.StatisticType
import com.example.vkapp.ui.theme.DarkRed


@Composable
fun PostCard(
    feedPost: FeedPost,
    onCommentClickListener: (StatisticItem) -> Unit,
    onLikeClickListener: (StatisticItem) -> Unit
) {
    Card {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            PostHeader(feedPost)
            Spacer(modifier = Modifier.height(8.dp))
            Text(feedPost.contentText, color = MaterialTheme.colors.onPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = feedPost.contentImageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistics(
                statistics = feedPost.statistics,
                onCommentClickListener = onCommentClickListener,
                onLikeClickListener = onLikeClickListener,
                isLiked = feedPost.isLiked
            )

        }


    }
}


@Composable
fun PostHeader(
    post: FeedPost
) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = post.communityImageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {

            Text(text = post.communityName)
            Text(text = post.publicationDate)

        }
        Icon(
            modifier = Modifier.padding(2.dp),
            imageVector = Icons.Default.MoreVert,
            contentDescription = null
        )


    }
}


@Composable
fun Statistics(
    statistics: List<StatisticItem>,
    onCommentClickListener: (StatisticItem) -> Unit,
    onLikeClickListener: (StatisticItem) -> Unit,
    isLiked: Boolean
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            val viewsType = statistics.getItemByType(StatisticType.VIEWS)

            IconWithText(
                iconResId = R.drawable.ic_views_count,
                text = formatStatisticCount(viewsType.count)
            )

        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            val likesType = statistics.getItemByType(StatisticType.LIKES)

            IconWithText(
                iconResId = if (isLiked)R.drawable.ic_like_set else R.drawable.ic_like,
                text = formatStatisticCount(likesType.count),
                onItemClickListener = {
                    onLikeClickListener(likesType)
                },
                tint = if (isLiked) DarkRed else MaterialTheme.colors.onSecondary

            )

            val commentsType = statistics.getItemByType(StatisticType.COMMENTS)

            IconWithText(
                iconResId = R.drawable.ic_comment,
                text = formatStatisticCount(commentsType.count),
                onItemClickListener = {
                    onCommentClickListener(commentsType)
                }
            )

            val sharesType = statistics.getItemByType(StatisticType.SHARES)

            IconWithText(
                iconResId = R.drawable.ic_share,
                text = formatStatisticCount(sharesType.count)
            )


        }


    }
}

private fun formatStatisticCount(count: Int): String {
    return if (count > 100_000) {
        String.format("%sK", (count / 1000))
    } else if (count > 1000) {
        String.format("%.1fK", (count / 1000f))
    } else {
        count.toString()
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException()
}

@Composable
fun IconWithText(
    iconResId: Int,
    text: String,
    onItemClickListener: (() -> Unit)? = null,
    tint: Color = MaterialTheme.colors.onSecondary
) {

    val modifier = if (onItemClickListener == null) {
        Modifier
    } else {
        Modifier.clickable {
            onItemClickListener()
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconResId),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(text = text)
    }
}