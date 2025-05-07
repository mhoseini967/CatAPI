package mohamad.hoseini.catapi.ui.feature.breeds

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import mohamad.hoseini.catapi.R
import mohamad.hoseini.catapi.domain.model.CatBreed
import mohamad.hoseini.catapi.ui.theme.lightFont
import mohamad.hoseini.catapi.ui.theme.mediumFont
import java.util.Locale

@Composable
fun CatBreedListRoute(
    navController: NavController,
    viewModel: BreedsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.event
            .collect { event ->
                when (event) {
                    is BreedsEvent.ShowMessage -> Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    null -> TODO()
                }

            }
    }

    CatBreedListScreen(
        state = state,
        navigateToBreedDetails = { id -> navController.navigate("breed_details/${id}") },
        onBreedsNeedRefresh = {
            viewModel.handleIntent(BreedsIntent.Refresh)
        },
        onSearchBreed = {
            viewModel.handleIntent(BreedsIntent.SearchBreed(it))
        },
        onChangingSearchMode = {
            viewModel.handleIntent(BreedsIntent.SetSearchingMode(it))
        }
    )
}


@Composable
fun CatBreedListScreen(
    state: BreedsState,
    navigateToBreedDetails: (breedId: String) -> Unit,
    onBreedsNeedRefresh: () -> Unit,
    onSearchBreed: (text: String) -> Unit,
    onChangingSearchMode: (isSearching: Boolean) -> Unit,
) {
    val breeds = state.breedsPagingFlow.collectAsLazyPagingItems()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AnimatedVisibility(state.searchMode) {
                SearchToolbar(
                    searchLoading = state.searchLoading,
                    onCloseClicked = { onChangingSearchMode(false) },
                    onValueChanged = { onSearchBreed(it) },
                    value = state.searchingText
                )
            }

            AnimatedVisibility(!state.searchMode) {
                HomeToolbar(
                    isDark = false,
                    onSearchClicked = { onChangingSearchMode(true) },
                    onThemeToggled = {}
                )
            }
        }
    ) { innerPadding ->


        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = rememberSwipeRefreshState(state.isRefreshing),
            onRefresh = { onBreedsNeedRefresh() }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 21.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                items(
                    breeds.itemCount,
                    key = { index -> breeds[index]?.id!! }
                ) { index ->
                    val breed = breeds[index]
                    breed?.let {
                        BreedItem(
                            breed,
                            onBreedClicked = {
                                navigateToBreedDetails(breed.breedId)
                            }
                        )
                    }

                }

            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BreedItem(breed: CatBreed, onBreedClicked: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onBreedClicked() },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, colorResource(R.color.borderColor)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(Modifier.padding(21.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(contentAlignment = Alignment.BottomEnd) {

                GlideImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = colorResource(R.color.inputColor)),
                    model = breed.imageUrl,
                    contentDescription = breed.name,
                    contentScale = ContentScale.FillWidth,
                )
                if (breed.isFavorite) {
                    Image(
                        modifier = Modifier.padding(8.dp),
                        painter = painterResource(R.drawable.ic_liked),
                        contentDescription = null
                    )
                }

            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(breed.name, fontFamily = mediumFont, fontSize = 16.sp)

                CountryFlag(breed.countryCode)
            }

            Text(
                breed.description,
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(R.color.accentColor),
                fontFamily = lightFont,
                fontSize = 13.sp,
                style = TextStyle(textAlign = TextAlign.Justify),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 20.sp

            )
        }
    }

}

@Composable
fun HomeToolbar(
    isDark: Boolean,
    onSearchClicked: () -> Unit,
    onThemeToggled: (isDark: Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { onThemeToggled(!isDark) },
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(R.drawable.ic_sun), contentDescription = null)
        }
        Text(stringResource(R.string.app_name), fontFamily = mediumFont)
        Box(
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { onSearchClicked() },
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(R.drawable.ic_search), contentDescription = null)
        }
    }

}


@Composable
fun SearchToolbar(
    value: String,
    onCloseClicked: () -> Unit,
    onValueChanged: (value: String) -> Unit,
    searchLoading: Boolean
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(21.dp)
            .height(60.dp),
        shape = RoundedCornerShape(percent = 50),
        border = BorderStroke(1.dp, colorResource(R.color.borderColor)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                value = value,
                placeholder = { Text("Search breed...", fontSize = 12.sp) },
                onValueChange = { onValueChanged(it) },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent

                ),
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .weight(1f)
            )

            if (searchLoading) {
                CircularProgressIndicator(
                    Modifier
                        .size(20.dp)
                        .padding(end = 8.dp),
                    strokeWidth = 1.5.dp
                )
            } else {
                Box(
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable { onCloseClicked() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(painter = painterResource(R.drawable.ic_close), contentDescription = null)
                }
            }

        }


    }

}


@Composable
fun CountryFlag(countryCode: String) {
    val flagEmoji = getFlagEmoji(countryCode)
    val countryName = Locale("", countryCode).displayCountry

    Text(
        text = "$flagEmoji $countryName",
        fontFamily = lightFont,
        fontSize = 10.sp,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .background(
                color = colorResource(R.color.inputColor),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 1.dp, horizontal = 8.dp)
    )
}

fun getFlagEmoji(countryCode: String): String {
    val countryCodeUpper = countryCode.uppercase()
    val firstLetter = countryCodeUpper[0] - 'A' + 0x1F1E6 // Unicode for the regional indicator "A"
    val secondLetter = countryCodeUpper[1] - 'A' + 0x1F1E6 // Unicode for the regional indicator "B"
    return String(Character.toChars(firstLetter) + Character.toChars(secondLetter))
}