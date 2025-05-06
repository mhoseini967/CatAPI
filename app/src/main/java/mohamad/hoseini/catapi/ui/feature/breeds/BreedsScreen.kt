package mohamad.hoseini.catapi.ui.feature.breeds

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    viewModel: BreedsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CatBreedListScreen(
        state = state,
        navigateToBreedDetails = {},
        onBreedsNeedRefresh = {
            viewModel.handleIntent(BreedsIntent.Refresh)
        }
    )
}


@Composable
fun CatBreedListScreen(
    state: BreedsState,
    navigateToBreedDetails: (breedId: String) -> Unit,
    onBreedsNeedRefresh: () -> Unit
) {
    val breeds = state.breedsPagingFlow.collectAsLazyPagingItems()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

        }
    ) { innerPadding ->


        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = rememberSwipeRefreshState(state.isRefreshing),
            onRefresh = {onBreedsNeedRefresh()}
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(21.dp),
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
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
                    .background(color = colorResource(R.color.inputColor))
                    .clip(RoundedCornerShape(8.dp)),
                model = breed.imageUrl,
                contentDescription = breed.name,
                contentScale = ContentScale.FillWidth,
            )


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
fun CountryFlag(countryCode: String) {
    val flagEmoji = getFlagEmoji(countryCode)
    val countryName = Locale("", countryCode).displayCountry

    Text(
        text = "$flagEmoji $countryName",
        fontFamily = lightFont,
        fontSize = 11.sp,
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