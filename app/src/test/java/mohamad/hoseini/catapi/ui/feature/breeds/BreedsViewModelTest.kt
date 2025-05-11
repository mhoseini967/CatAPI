@file:OptIn(ExperimentalCoroutinesApi::class)

package mohamad.hoseini.catapi.ui.feature.breeds

import androidx.paging.PagingData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import mohamad.hoseini.catapi.CoroutineTestRule
import mohamad.hoseini.catapi.data.local.preferences.SharedPreferencesDataSource
import mohamad.hoseini.catapi.domain.model.Settings
import mohamad.hoseini.catapi.domain.usecase.GetPaginatedCatBreedsUseCase
import mohamad.hoseini.catapi.domain.usecase.RefreshCatBreedsUseCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@RunWith(JUnit4::class)
class BreedsViewModelTest {

    @RelaxedMockK
    lateinit var refreshCatBreedsUseCase: RefreshCatBreedsUseCase

    @RelaxedMockK
    lateinit var getPaginatedCatBreedsUseCase: GetPaginatedCatBreedsUseCase

    @RelaxedMockK
    lateinit var sharedPreferencesDataSource: SharedPreferencesDataSource


    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when user searches repeatedly, should not trigger multiple requests`() = runTest {
        coEvery { getPaginatedCatBreedsUseCase() } returns flowOf(PagingData.empty())

        val viewModel = createViewModel()
        viewModel.handleIntent(BreedsIntent.SetSearchingMode(true))

        "bengal".forEach {
            viewModel.handleIntent(BreedsIntent.SearchBreed(it.toString()))
            advanceTimeBy(100)
        }

        advanceTimeBy(300)

        coVerify(exactly = 1) {getPaginatedCatBreedsUseCase(any())}
    }

    @Test
    fun `when refresh breeds has error, show error message event`() = runTest {
        val viewModel = createViewModel()

        val mockedErrorMessage = "Error!"
        coEvery { refreshCatBreedsUseCase() } returns flowOf(
            Result.failure(
                Exception(
                    mockedErrorMessage
                )
            )
        )

        val collectedEvents = mutableListOf<BreedsEvent>()
        val job = launch {
            viewModel.event.collect { event ->
                event?.let { collectedEvents.add(it) }
            }
        }

        viewModel.handleIntent(BreedsIntent.Refresh)
        advanceUntilIdle()

        assertFalse { viewModel.state.value.isRefreshing }
        assertEquals(
            listOf<BreedsEvent>(BreedsEvent.ShowMessage(mockedErrorMessage)),
            collectedEvents
        )

        job.cancel()
    }

    @Test
    fun `when theme toggle is triggered, should update state to dark mode`() = runTest {
        val viewModel = createViewModel()

        every { sharedPreferencesDataSource.getSettings() } returns Settings(isDarkMode = false)
        viewModel.handleIntent(BreedsIntent.ToggleTheme)

        advanceUntilIdle()

        assertEquals(true, viewModel.state.value.isDarkMode)
        verify { sharedPreferencesDataSource.saveSettings(any()) }

    }

    @Test
    fun `when multiple theme toggles are triggered, should update state correctly each time`() =
        runTest {
            val viewModel = createViewModel()

            every { sharedPreferencesDataSource.getSettings() } returns Settings(isDarkMode = false)
            viewModel.handleIntent(BreedsIntent.ToggleTheme)

            every { sharedPreferencesDataSource.getSettings() } returns Settings(isDarkMode = true)
            viewModel.handleIntent(BreedsIntent.ToggleTheme)

            advanceUntilIdle()

            assertEquals(false, viewModel.state.value.isDarkMode)
            verify { sharedPreferencesDataSource.saveSettings(any()) }

        }

    private fun createViewModel() = BreedsViewModel(
        refreshCatBreedsUseCase,
        getPaginatedCatBreedsUseCase,
        sharedPreferencesDataSource
    )
}