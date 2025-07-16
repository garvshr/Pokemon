package com.garv.pokemon

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

data class CategoriesResponse(val results: List<pokemonData>)

@Parcelize
data class pokemonData(
    val name: String,
    val url :String
) : Parcelable

sealed class AppEntry( val title: String, val route: String ) {

    sealed class DrawerScreen(val dTitle: String, val dRoute: String, @DrawableRes val dIcon: Int): AppEntry(title = dTitle, route = dRoute) {

        object DisplayMode: DrawerScreen(
            "Dark Mode",
            "darkmode",
            R.drawable.baseline_dark_mode_24
        )

        object Favourites: DrawerScreen(
            "Favourites",
            "favourites",
            R.drawable.outline_favorite_24
        )
    }
}

sealed class NavScreen(val route: String) {
    object Home : NavScreen("home")
    object Details : NavScreen("details")
}
