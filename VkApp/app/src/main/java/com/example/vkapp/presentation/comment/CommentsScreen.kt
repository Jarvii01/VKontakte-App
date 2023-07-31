package com.example.vkapp.presentation.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.vkapp.R
import com.example.vkapp.domain.entity.FeedPost
import com.example.vkapp.domain.entity.PostComment
import com.example.vkapp.presentation.NewsFeedApplication
import com.example.vkapp.presentation.getApplicationComponent
import com.example.vkapp.ui.theme.DarkBlue


@Composable
fun CommentsScreen(
    onBackPressed: () -> Unit,
    feedPost: FeedPost

) {

    val component = getApplicationComponent()
        .getCommentsScreenComponentFactory()
        .create(feedPost)

    val viewModel: CommentsViewModel = viewModel(factory = component.getViewModelFactory())

    val screenState = viewModel.screenState.collectAsState(CommentsScreenState.Initial)



    when (val currentState = screenState.value) {
        is CommentsScreenState.Comments -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = stringResource(R.string.comments))
                        },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = null
                                )

                            }
                        }

                    )
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier.padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 72.dp
                    )
                ) {
                    items(
                        items = currentState.comment,
                        key = { it.id }
                    ) { comment ->
                        CommentItem(comments = comment)
                    }
                }

            }
        }

        is CommentsScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }

        is CommentsScreenState.Initial -> {}

    }
}


@Composable
fun CommentItem(
    comments: PostComment
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            modifier = Modifier.size(24.dp),
            model = comments.authorAvatarUrl,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = comments.authorName,
                fontSize = 12.sp
            )

            Text(
                text = comments.commentText,
                fontSize = 14.sp
            )

            Text(
                text = comments.publicationDate,
                fontSize = 12.sp
            )

        }

    }


}