package com.garv.pokemon

import HomeView
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun AppNavGraph(
    navController: NavHostController,
    onDrawerClick: () -> Unit
)
{
    NavHost(
        navController = navController,
        startDestination = NavScreen.Home.route
    ) {
        composable(NavScreen.Home.route) {
            HomeView(
                onDrawerClick = onDrawerClick,
                onPokemonClick = {
                    pokemon -> navController.currentBackStackEntry?.savedStateHandle?.set( "pokemon",pokemon)
                    navController.navigate(NavScreen.Details.route)
                }
            )
        }

        composable(NavScreen.Details.route) {
            val pokemon = navController.previousBackStackEntry?.savedStateHandle?.get<pokemonData>("pokemon")

            DetailScreen(pokemon)
        }
    }
}