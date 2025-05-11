@file:OptIn(ExperimentalCoroutinesApi::class)

package mohamad.hoseini.catapi.ui.feature.detail

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import mohamad.hoseini.catapi.CoroutineTestRule
import mohamad.hoseini.catapi.domain.model.CatBreed
import mohamad.hoseini.catapi.domain.usecase.ChangeBreedFavoriteParams
import mohamad.hoseini.catapi.domain.usecase.ChangeBreedFavoriteUseCase
import mohamad.hoseini.catapi.domain.usecase.GetCatBreedDetailsUseCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@RunWith(JUnit4::class)
class BreedDetailsViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @RelaxedMockK
    lateinit var changeBreedFavoriteUseCase: ChangeBreedFavoriteUseCase


    @RelaxedMockK
    lateinit var getCatBreedDetailsUseCase: GetCatBreedDetailsUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `toggleLike when breed is not null updates breed's favorite state`() = runTest {
        val breedId = "testId"
        val initialBreed = createBreedDetails(breedId)

        val viewModel = createViewModel()
        viewModel.setState(BreedDetailsState(breed = initialBreed))


        coEvery { changeBreedFavoriteUseCase(any()) } returns flowOf(Unit)

        viewModel.handleIntent(BreedDetailsIntent.ToggleLike)

        advanceUntilIdle()

        coVerify {
            changeBreedFavoriteUseCase(ChangeBreedFavoriteParams(breedId = breedId, isFavorite = true))
        }

        assertEquals(true, viewModel.state.value.breed?.isFavorite)
    }


    @Test
    fun `should update state with breed details when fetching breed details`() = runTest {
        val breedId = "testId"
        val mockCatBreed = createBreedDetails(breedId)

        coEvery { getCatBreedDetailsUseCase(breedId) } returns flowOf(mockCatBreed)

        val viewModel = createViewModel()

        viewModel.handleIntent(BreedDetailsIntent.FetchBreedDetails(breedId))

        advanceUntilIdle()

        assertEquals(mockCatBreed, viewModel.state.value.breed)
        assertFalse { viewModel.state.value.isLoading }

        coVerify { getCatBreedDetailsUseCase(breedId) }

    }

    @Test
    fun `toggleLike when breed is null does not trigger use case`() = runTest {
        val viewModel = mockk<BreedDetailsViewModel>(relaxed = true)

        every { viewModel.state } returns MutableStateFlow(BreedDetailsState(breed = null))

        viewModel.handleIntent(BreedDetailsIntent.ToggleLike)

        coVerify(exactly = 0) { changeBreedFavoriteUseCase(any()) }
    }



    private fun createBreedDetails(breedId: String = "sample") = CatBreed(
        id = 1,
        breedId = breedId,
        name = "Persian",
        description = "A calm and loving breed",
        temperaments = listOf("Calm", "Affectionate"),
        origin = "Iran",
        countryCode = "IR",
        lifeSpan = "12-17 years",
        weight = "3 - 5",
        adaptability = 4,
        affectionLevel = 5,
        childFriendly = 3,
        wikipediaUrl = "https://en.wikipedia.org/wiki/Persian_cat",
        imageUrl = "https://example.com/persian_image.jpg",
        isFavorite = false
    )

    private fun createViewModel() =
        BreedDetailsViewModel(getCatBreedDetailsUseCase, changeBreedFavoriteUseCase)


}