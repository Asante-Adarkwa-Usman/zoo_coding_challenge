package com.ghost.comcast_coding_assignment.data.model


import com.ghost.comcast_coding_assignment.utils.AnimalType

data class AnimalListItemModel(
    val characteristics: CharacteristicsModel = CharacteristicsModel(),
    val locations: List<String> = listOf(),
    val name: String = "",
    val taxonomy: TaxonomyModel = TaxonomyModel(),
    @Transient val type: AnimalType
)