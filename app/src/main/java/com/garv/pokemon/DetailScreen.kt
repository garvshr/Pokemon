package com.garv.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter


@Composable
fun DetailScreen(pokemon: pokemonData?) {

    var detail by remember { mutableStateOf<PokemonDetail?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var description by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(pokemon?.name) {
        try {
            if (pokemon != null) {
                val details = pokemonService.getPokemonDetail(pokemon.name)
                val species = pokemonService.getPokemonSpecies(pokemon.name)

                val englishEntry = species.flavor_text_entries.firstOrNull {
                    it.language.name == "en"
                }

                detail = details
                description = englishEntry?.flavor_text
                    ?.replace("\n", " ")
            }

        } catch (e: Exception) {
            error = e.message
        }
    }

    when {
        detail != null -> {
            val data = detail!!
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .navigationBarsPadding()
                    .statusBarsPadding()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val id = pokemon?.url?.trimEnd('/')?.split("/")?.lastOrNull()
                val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
                item { HeaderCard(data.name, imageUrl) }

                item { BasicInfoCard(types = data.types, xp =  data.base_experience, height =  data.height, weight =  data.weight) }
                item { StatsCard(data.stats) }
                item { AbilitiesCard(data.abilities) }
//                item { EvolutionCard() }
                if (!description.isNullOrBlank()) {
                    item { DescriptionCard(description!!) }
                }

            }
        }

        error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $error")
            }
        }

        else -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }


}


@Composable
fun HeaderCard(name: String, imageURL: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageURL),
                    contentDescription = name,
                    modifier = Modifier.size(160.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = name.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Composable
fun BasicInfoCard(height: Int, weight: Int, xp: Int, types: List<TypeSlot>) {
    val typeText = types.joinToString { it.type.name }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(4.dp)){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Basic Info", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Type: ${typeText}")
                Text("Base XP: $xp")
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Height: ${height / 10.0}m")
                Text("Weight: ${weight / 10.0}m")
            }
        }
    }
}

@Composable
fun StatsCard(stats: List<StatSlot>) {
    @Composable
    fun StatRow(name: String, value: Int) {
        Column {
            Text("$name: $value")
            LinearProgressIndicator(
                progress = value / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            Spacer(Modifier.height(8.dp))
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(4.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Stats", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            stats.forEach {
                StatRow(it.stat.name.replaceFirstChar { it.uppercase() }, it.base_stat)
            }
        }
    }



}

@Composable
fun AbilitiesCard(abilities: List<AbilitySlot>) {

    @Composable
    fun Chip(text: String) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(50))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(text = text, fontSize = 14.sp)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Abilities", fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                abilities.forEach {
                    Chip(it.ability.name)
                }
            }
        }
    }
}

@Composable
fun EvolutionCard() {
    Card {
        Column(Modifier.padding(16.dp)) {
            Text("Evolution", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("Pichu → Pikachu → Raichu")
        }
    }
}

@Composable
fun DescriptionCard(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Description", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(text)
        }
    }
}


