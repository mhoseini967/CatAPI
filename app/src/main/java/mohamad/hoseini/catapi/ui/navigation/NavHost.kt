package mohamad.hoseini.catapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mohamad.hoseini.catapi.ui.feature.breeds.CatBreedListRoute
import mohamad.hoseini.catapi.ui.feature.detail.BreedDetailsRoute

@Composable
fun CatNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "breeds") {
        composable("breeds") {
            CatBreedListRoute(navController)
        }

        composable("breed_details/{breedId}") { backStackEntry ->
            // Retrieve breedId from the navigation arguments
            val breedId = backStackEntry.arguments?.getString("breedId")
            breedId?.let {
                BreedDetailsRoute(navController, breedId = breedId)
            }
        }

    }
}