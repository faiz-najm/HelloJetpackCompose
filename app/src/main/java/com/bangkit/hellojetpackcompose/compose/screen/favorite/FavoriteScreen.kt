package com.bangkit.hellojetpackcompose.compose.screen.favorite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bangkit.hellojetpackcompose.R
import com.bangkit.hellojetpackcompose.data.local.entity.FavoriteUserEntity
import com.bangkit.hellojetpackcompose.compose.common.UiState
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = { viewModel.searchFavoriteUser(it) },
            onBackClick = onBackClick,
            modifier = Modifier.background(MaterialTheme.colorScheme.primary)
        )


        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                is UiState.Success -> {
                    if (uiState.data.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = stringResource(R.string.no_data),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(16.dp),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        FavoriteContent(
                            itemsList = uiState.data,
                            modifier = modifier,
                            navigateToDetail = navigateToDetail,
                        )
                    }
                }

                is UiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = stringResource(R.string.no_connection),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteContent(
    itemsList: List<FavoriteUserEntity>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
) {

    Box(modifier = modifier.fillMaxSize()) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(itemsList, key = { it.id }) { user ->
                UserListItem(
                    name = user.name,
                    photoUrl = user.avatarUrl,
                    modifier = Modifier
                        .clickable {
                            navigateToDetail(user.username)
                        }
                        .fillMaxWidth()
                        .animateItemPlacement(tween(durationMillis = 100))
                        .padding(horizontal = 20.dp)

                )
            }

        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }
            )
        }
    }

}


@Composable
fun UserListItem(
    name: String,
    photoUrl: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )
        Text(
            text = name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
        )
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = false,
        onActiveChange = {},
        leadingIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                )

            }
        },
        placeholder = {
            Text(stringResource(R.string.search_hero))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun FavoriteContentPreview() {
    FavoriteContent(
        itemsList = listOf(
            FavoriteUserEntity(
                username = "username",
                name = "name",
                avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            ),
            FavoriteUserEntity(
                username = "username1",
                name = "name",
                avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            ),
            FavoriteUserEntity(
                username = "username2",
                name = "name",
                avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            ),
            FavoriteUserEntity(
                username = "username3",
                name = "name",
                avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            ),
            FavoriteUserEntity(
                username = "username4",
                name = "name",
                avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            ),
            FavoriteUserEntity(
                username = "username5",
                name = "name",
                avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            ),
            FavoriteUserEntity(
                username = "username6",
                name = "name",
                avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            ),
            FavoriteUserEntity(
                username = "username",
                name = "name",
                avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            )
        ),
        navigateToDetail = {}
    )
}