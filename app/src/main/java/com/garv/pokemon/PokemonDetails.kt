package com.garv.pokemon

data class PokemonDetail(
    val name: String,
    val height: Int,
    val weight: Int,
    val base_experience: Int,
    val types: List<TypeSlot>,
    val stats: List<StatSlot>,
    val abilities: List<AbilitySlot>
)

data class TypeSlot(val type: Type)
data class Type(val name: String)

data class StatSlot(val base_stat: Int, val stat: Stat)
data class Stat(val name: String)

data class AbilitySlot(val ability: Ability)
data class Ability(val name: String)


data class PokemonSpecies(
    val flavor_text_entries: List<FlavorTextEntry>
)

data class FlavorTextEntry(
    val flavor_text: String,
    val language: Language
)

data class Language(
    val name: String
)
