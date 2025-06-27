package com.ghost.comcast_coding_assignment.data.remote

import com.ghost.comcast_coding_assignment.data.model.AnimalListItemModel
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    //Get animals
    @GET(ApiReference.ANIMAL_END_POINT)
    suspend fun getAnimals(
        @Query("name") name: String,
    ): Response<ArrayList<AnimalListItemModel>>
}