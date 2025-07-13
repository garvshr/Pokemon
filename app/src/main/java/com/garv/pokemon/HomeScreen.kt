import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.garv.pokemon.MainViewModel
import com.garv.pokemon.pokemonItem
import androidx.lifecycle.viewmodel.compose.viewModel
import com.garv.pokemon.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(paddingValues: PaddingValues) {

    val viewModel: MainViewModel = viewModel()
    val state = viewModel.categoriesState.value

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(painter = painterResource(R.drawable.pokemon_01),
                        contentDescription = "logo",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(start = 16.dp))
                },
                title = {},
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        },
        containerColor = Color.White
    ) {

        when {
            state.loading -> Box(modifier = Modifier
                .fillMaxSize()
                .padding(it), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            state.error != null -> Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), contentAlignment = Alignment.Center) {
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
                    items(state.list) {
                            item ->
                        pokemonItem(item)
                    }
                }
            }

        }

    }
}
