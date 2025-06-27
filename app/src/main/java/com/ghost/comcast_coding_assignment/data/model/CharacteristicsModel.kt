package com.ghost.comcast_coding_assignment.data.model


import com.google.gson.annotations.SerializedName

data class CharacteristicsModel(
    @SerializedName("age_of_sexual_maturity")
    val ageOfSexualMaturity: String? = null,
    @SerializedName("age_of_weaning")
    val ageOfWeaning: String? = null,
    @SerializedName("average_clutch_size")
    val averageClutchSize: String? = null,
    @SerializedName("average_litter_size")
    val averageLitterSize: String? = null,
    @SerializedName("biggest_threat")
    val biggestThreat: String? = null,
    val color: String? = null,
    @SerializedName("common_name")
    val commonName: String? = null,
    val diet: String = "",
    @SerializedName("distinctive_feature")
    val distinctiveFeature: String? = null,
    @SerializedName("estimated_population_size")
    val estimatedPopulationSize: String? = null,
    @SerializedName("favorite_food")
    val favoriteFood: String? = null,
    @SerializedName("gestation_period")
    val gestationPeriod: String? = null,
    val group: String? = null,
    @SerializedName("group_behavior")
    val groupBehavior: String? = null,
    val habitat: String? = null,
    val height: String? = null,
    val length: String? = null,
    val lifespan: String? = null,
    val lifestyle: String? = null,
    @SerializedName("litter_size")
    val litterSize: String? = null,
    val location: String? = null,
    @SerializedName("main_prey")
    val mainPrey: String? = null,
    @SerializedName("most_distinctive_feature")
    val mostDistinctiveFeature: String? = null,
    @SerializedName("name_of_young")
    val nameOfYoung: String? = null,
    @SerializedName("number_of_species")
    val numberOfSpecies: String? = null,
    @SerializedName("optimum_ph_level")
    val optimumPhLevel: String? = null,
    val origin: String? = null,
    @SerializedName("other_name(s)")
    val otherNames: String? = null,
    val predators: String? = null,
    val prey: String? = null,
    @SerializedName("skin_type")
    val skinType: String? = null,
    val slogan: String? = null,
    val temperament: String? = null,
    @SerializedName("top_speed")
    val topSpeed: String? = null,
    val training: String? = null,
    val type: String? = null,
    @SerializedName("water_type")
    val waterType: String? = null,
    val weight: String? = null,
    val wingspan: String? = null
)