package com.ghost.comcast_coding_assignment.domain.repository

import com.ghost.comcast_coding_assignment.data.model.AnimalsItemModelListWrapper
import com.ghost.comcast_coding_assignment.utils.UiStatus
import kotlinx.coroutines.flow.Flow

interface AnimalRepository {
   suspend fun getAllAnimals(): Flow<UiStatus<AnimalsItemModelListWrapper>>
}