package com.ghost.comcast_coding_assignment.data.repository

import android.util.Log
import com.ghost.comcast_coding_assignment.data.model.AnimalListItemModel
import com.ghost.comcast_coding_assignment.data.model.AnimalsItemModelListWrapper
import com.ghost.comcast_coding_assignment.data.remote.ApiService
import com.ghost.comcast_coding_assignment.domain.repository.AnimalRepository
import com.ghost.comcast_coding_assignment.utils.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.supervisorScope
import java.util.ArrayList
import javax.inject.Inject

class AnimalRepositoryImpl @Inject constructor(
    private val apiService: ApiService
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

            val contentType = response.headers()["Content-Type"]
            if (!response.isSuccessful || contentType?.contains("html", true) == true) {
                Log.e("AnimalRepository", "Invalid response for $name: Content-Type = $contentType")
                return emptyList()
            }

            val body = response.body()
            if (body != null) {
                body.map { it.copy(type = type) }
            } else {
                Log.e("AnimalRepository", "Null body for $name")
                emptyList()
            }

        } catch (e: Exception) {
            Log.e("AnimalRepository", "Error fetching $name: ${e.localizedMessage}")
            emptyList()
        }
    }

}