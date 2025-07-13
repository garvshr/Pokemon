package com.garv.pokemon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _categoriesState = mutableStateOf(pokemonState())
    val categoriesState get() = _categoriesState


    init {
        viewModelScope.launch {
            try {
                val response = pokemonService.getCategories()
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    list = response.results,
                    error = null
                )
            } catch (e : Exception) {
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    error = "Error fetching the categories ${e.message}"
                )
            }
        }
    }
    var searchQuery by mutableStateOf("")
        private set

    fun onSearchQueryChanged(newQuery: String) {
        searchQuery = newQuery
    }

    val filteredList: List<pokemonData>
        get() = if(searchQuery.isBlank()) {
            categoriesState.value.list
        } else {
            categoriesState.value.list.filter {
                it.name.contains(searchQuery.trim(), ignoreCase = true)
            }
        }
}

data class pokemonState(
    val loading: Boolean = true,
    val list: List<pokemonData> = emptyList(),
    val error: String? = null
)