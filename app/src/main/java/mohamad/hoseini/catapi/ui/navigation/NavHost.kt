package mohamad.hoseini.catapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mohamad.hoseini.catapi.ui.feature.breeds.CatBreedListRoute

@Composable
fun CatNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "breeds") {
        composable("breeds") {
            CatBreedListRoute()
        }

    }
}