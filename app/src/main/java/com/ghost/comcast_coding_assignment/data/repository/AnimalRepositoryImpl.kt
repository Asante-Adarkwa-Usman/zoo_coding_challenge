package com.ghost.comcast_coding_assignment.data.repository

import android.util.Log
import com.ghost.comcast_coding_assignment.data.model.AnimalListItemModel
import com.ghost.comcast_coding_assignment.data.model.AnimalsItemModelListWrapper
import com.ghost.comcast_coding_assignment.data.remote.ApiService
import com.ghost.comcast_coding_assignment.domain.repository.AnimalRepository
import com.ghost.comcast_coding_assignment.utils.AnimalType
import com.ghost.comcast_coding_assignment.utils.UiStatus
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.supervisorScope
import java.util.ArrayList
import javax.inject.Inject

class AnimalRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val gson : Gson
) : AnimalRepository {
    //In-Memory Caching
    private var cachedAnimals: AnimalsItemModelListWrapper? = null
    private var lastFetchTimeMillis: Long = 0L

    private val cacheDurationMillis = 10 * 60 * 1000 // 10 minutes duration


    override suspend fun getAllAnimals(): Flow<UiStatus<AnimalsItemModelListWrapper>> = flow {
        emit(UiStatus.Loading)
        val currentTime = System.currentTimeMillis()

        // Return cached data if within 10 minutes
        if (cachedAnimals != null && currentTime - lastFetchTimeMillis <= cacheDurationMillis) {
            emit(UiStatus.Success(cachedAnimals!!))
            return@flow
        }

        try {
            supervisorScope {
                val dogDeferred = async { fetchAnimals(AnimalType.DOG.type, AnimalType.DOG) }
                val birdDeferred = async { fetchAnimals(AnimalType.BIRD.type, AnimalType.BIRD) }
                val bugDeferred = async { fetchAnimals(AnimalType.BUG.type, AnimalType.BUG) }

                val animalList = dogDeferred.await() + birdDeferred.await() + bugDeferred.await()
                if (animalList.isNotEmpty()) {
                    val wrapper = AnimalsItemModelListWrapper(ArrayList(animalList))
                    cachedAnimals = wrapper
                    lastFetchTimeMillis = currentTime
                    emit(UiStatus.Success(wrapper))
                } else {
                    emit(UiStatus.Error("Failed to fetch animals data"))
                }
            }
        } catch (e: Exception) {
            emit(UiStatus.Error(e.localizedMessage ?: "Unknown error"))
        }
    }


    //Fetch animals function

    private suspend fun fetchAnimals(
        name: String,
        type: AnimalType
    ): List<AnimalListItemModel> {
        return try {
            val response = apiService.getAnimals(name)
            val rawBody = response.body()
            Log.d("AnimalRepository", "API response for $name: $rawBody")

            if (response.isSuccessful && rawBody != null) {
                val jsonElement = JsonParser.parseString(rawBody.toString())
                if (jsonElement.isJsonArray) {
                    val listType = object : TypeToken<List<AnimalListItemModel>>() {}.type
                    gson.fromJson<List<AnimalListItemModel>>(jsonElement, listType)
                        .map { it.copy(type = type) }
                } else {
                    Log.e("AnimalRepository", "Unexpected response for $name: $rawBody")
                    emptyList()
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("AnimalRepository", "Error body for $name: $e")
            emptyList()
        }
    }

}