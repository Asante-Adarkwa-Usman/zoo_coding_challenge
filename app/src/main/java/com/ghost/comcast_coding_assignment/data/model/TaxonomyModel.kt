package com.ghost.comcast_coding_assignment.data.model


import com.google.gson.annotations.SerializedName

data class TaxonomyModel(
    @SerializedName("class")
    val classX: String = "",
    val family: String = "",
    val genus: String = "",
    val kingdom: String = "",
    val order: String = "",
    val phylum: String = "",
    @SerializedName("scientific_name")
    val scientificName: String? = null
)