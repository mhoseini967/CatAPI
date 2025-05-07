@file:OptIn(ExperimentalGlideComposeApi::class)

package mohamad.hoseini.catapi.ui.feature.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import mohamad.hoseini.catapi.R
import mohamad.hoseini.catapi.domain.model.CatBreed
import mohamad.hoseini.catapi.ui.component.DashedDivider
import mohamad.hoseini.catapi.ui.component.Like
import mohamad.hoseini.catapi.ui.component.PrimaryButton
import mohamad.hoseini.catapi.ui.component.Toolbar
import mohamad.hoseini.catapi.ui.feature.breeds.CountryFlag
import mohamad.hoseini.catapi.ui.theme.lightFont
import mohamad.hoseini.catapi.ui.theme.mediumFont
import mohamad.hoseini.catapi.utils.BrowserUtils


@Composable
fun BreedDetailsRoute(
    navController: NavController,
    viewModel: BreedDetailsViewModel = hiltViewModel(),
    breedId: String
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.event
            .collect { event ->
                when (event) {
                    is BreedDetailsEvent.ShowMessage -> Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    null -> {}
                }

            }
    }

    LaunchedEffect(breedId) {
        viewModel.handleIntent(BreedDetailsIntent.FetchBreedDetails(breedId))
    }

    BreedDetailsScreen(
        state = state,
        onOpenWikipediaUrl = {
            BrowserUtils.openUrl(context, it)
        }, onBackClicked = {
            navController.popBackStack()
        },
        onToggleLike = {
            viewModel.handleIntent(BreedDetailsIntent.ToggleLike)
        }
    )
}

@Composable
fun BreedDetailsScreen(
    state: BreedDetailsState,
    onOpenWikipediaUrl: (url: String) -> Unit,
    onBackClicked: () -> Unit,
    onToggleLike: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            Toolbar(onBackClicked = onBackClicked) {
                Like(state.breed?.isFavorite ?: false, onLikeToggle = { onToggleLike() })
            }
        }
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                state.breed?.let { breed ->
                    Column(
                        Modifier
                            .padding(horizontal = 36.dp)
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        Spacer(Modifier.height(10.dp))

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


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(breed.name, fontFamily = mediumFont, fontSize = 20.sp)

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

                        BreedDetailsInformation(breed)

                        if (breed.temperaments.isNotEmpty()) {
                            Column {
                                Text("Temperament", fontFamily = mediumFont, fontSize = 13.sp)
                                Spacer(Modifier.height(16.dp))
                                TemperamentGrid(breed.temperaments)
                            }
                        }

                        breed.wikipediaUrl?.let {
                            PrimaryButton(
                                text = "View wikipedia",
                                onClick = {
                                    onOpenWikipediaUrl(breed.wikipediaUrl)
                                }
                            )
                        }

                        Spacer(Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun TemperamentGrid(temperaments: List<String>) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)

    ) {
        temperaments.forEach { temperament ->
            TagItem(tag = temperament)
        }
    }
}

@Composable
fun TagItem(tag: String) {
    Text(
        text = tag,
        modifier = Modifier
            .background(
                color = colorResource(R.color.inputColor),
                shape = RoundedCornerShape(percent = 50)
            )
            .padding(horizontal = 8.dp, vertical = 3.dp)
            .wrapContentWidth(),
        fontFamily = lightFont,
        color = Color.Black,
    )
}

@Composable
fun BreedDetailsInformation(breed: CatBreed) {
    Column(verticalArrangement = Arrangement.spacedBy(21.dp)) {
        KeyValueInformation("Life Span", "${breed.lifeSpan} Year")
        KeyValueInformation("Weight", "${breed.weight} Kg")
        KeyValueInformation("Adaptability", getPointPercentage(breed.adaptability))
        KeyValueInformation("Affection Level", getPointPercentage(breed.affectionLevel))
        KeyValueInformation("Child Friendly", getPointPercentage(breed.childFriendly))
    }
}

private fun getPointPercentage(point: Int): String {
    return "${(point * 100) / 5}%"
}


@Composable
fun KeyValueInformation(key: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Text(
            key,
            fontFamily = lightFont,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        DashedDivider(
            color = colorResource(R.color.accentColor),
            dashHeight = 0.2.dp,
            modifier = Modifier.weight(1f),
        )
        Text(
            value,
            fontFamily = lightFont,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.secondary
        )

    }
}