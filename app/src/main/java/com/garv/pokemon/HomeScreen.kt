
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.garv.pokemon.MainViewModel
import com.garv.pokemon.pokemonItem
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.garv.pokemon.AppEntry
import com.garv.pokemon.AppNavGraph
import com.garv.pokemon.Drawer
import com.garv.pokemon.R
import com.garv.pokemon.pokemonData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(onDrawerClick: ()-> Unit, onPokemonClick: (pokemonData) -> Unit) {

    val viewModel: MainViewModel = viewModel()
    val state = viewModel.categoriesState.value
    var isSearching by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (!isSearching) {
                        IconButton(onClick = onDrawerClick) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Drawer",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                },
                title = {
                    if (isSearching) {
                        val focusRequester = remember { FocusRequester() }
                        val keyboardController = LocalSoftwareKeyboardController.current

                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                            keyboardController?.show()
                        }

                        TextField(
                            value = viewModel.searchQuery,
                            onValueChange = viewModel::onSearchQueryChanged,
                            placeholder = { Text("Search Pokémon") },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 16.sp),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 16.dp)
                                .focusRequester(focusRequester)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.pokemon_01),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(48.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isSearching = !isSearching
                        if (!isSearching) {
                            viewModel.onSearchQueryChanged("")
                        }
                    }) {
                        Icon(
                            imageVector = if (isSearching) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            )

        },
        containerColor = MaterialTheme.colorScheme.background
    ) {

        when {
            state.loading -> Box(modifier = Modifier
                .fillMaxSize()
                .padding(it), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            state.error != null -> Box(modifier = Modifier
                .fillMaxSize()
                .padding(it), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${state.error}")
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.filteredList) {
                            item ->
                        pokemonItem(item) {
                            onPokemonClick(item)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MainScreenWithDrawer() {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isDarkTheme by remember { mutableStateOf(false) }

    val navController = rememberNavController()

    val DrawerItems = listOf(
        AppEntry.DrawerScreen.DisplayMode,
        AppEntry.DrawerScreen.Favourites
    )

    MaterialTheme(colorScheme = if(isDarkTheme) darkColorScheme() else lightColorScheme()) {

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent =  {
                Drawer(
                    items = DrawerItems,
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { isDarkTheme = !isDarkTheme },
                    onItemClick = {}
                )
            }
        ) {
            AppNavGraph(navController = navController, onDrawerClick = {scope.launch { drawerState.open() }})
        }
    }
}
