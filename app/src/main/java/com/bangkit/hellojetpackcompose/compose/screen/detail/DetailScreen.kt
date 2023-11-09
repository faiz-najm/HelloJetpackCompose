package com.bangkit.hellojetpackcompose.compose.screen.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bangkit.hellojetpackcompose.R
import com.bangkit.hellojetpackcompose.data.local.entity.FavoriteUserEntity
import com.bangkit.hellojetpackcompose.model.User
import com.bangkit.hellojetpackcompose.compose.theme.JetProfileDiscordTheme
import com.bangkit.hellojetpackcompose.compose.common.UiState

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailProfileViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val isFavorite = viewModel.isFavorite.collectAsState(initial = false).value

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getDetailUser()
            }

            is UiState.Success -> {
                DetailContent(
                    uiState.data,
                    modifier = modifier,
                    onBackClick = navigateBack,
                    fabOnClicked = {
                        if (isFavorite) {
                            viewModel.deleteFavoriteUser(
                                FavoriteUserEntity(
                                    id = uiState.data.id,
                                    username = uiState.data.login,
                                    name = uiState.data.name ?: uiState.data.login,
                                    avatarUrl = uiState.data.avatarUrl
                                )
                            )

                        } else {
                            viewModel.insertFavoriteUser(
                                FavoriteUserEntity(
                                    id = uiState.data.id,
                                    username = uiState.data.login,
                                    name = uiState.data.name ?: uiState.data.login,
                                    avatarUrl = uiState.data.avatarUrl
                                )
                            )
                        }
                    },
                    statusFavorite = isFavorite
                )
            }

            is UiState.Error -> {

            }
        }
    }

}

@Composable
fun DetailContent(
    data: User,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    fabOnClicked: () -> Unit,
    statusFavorite: Boolean,
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = fabOnClicked,
                modifier = Modifier
                    .padding(bottom = 80.dp)
            ) {
                if (statusFavorite) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = stringResource(R.string.favorite),
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.favorite),
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = modifier
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()

            ) {
                AsyncImage(
                    model = data.avatarUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.Center)
                        .clip(CircleShape)
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = data.name ?: data.login,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),

                    )
                Text(
                    text = data.login,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = FontStyle.Italic
                    ),

                    )
                Row {
                    Text(
                        text = stringResource(R.string.followers, data.followers ?: 0),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = stringResource(R.string.following, data.following ?: 0),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    JetProfileDiscordTheme {
        DetailContent(
            data = User(
                name = "Rizki Ramadhan",
                login = "rizkiramadhan",
                avatarUrl = "https://avatars.githubusercontent.com/u/43180040?v=4",
                followers = 10,
                following = 10,
                company = "Bangkit",
                location = "Jakarta",
                publicRepos = 10,
                publicGists = 10,
                id = 10,
            ),
            onBackClick = {},
            fabOnClicked = {},
            statusFavorite = false
        )
    }
}