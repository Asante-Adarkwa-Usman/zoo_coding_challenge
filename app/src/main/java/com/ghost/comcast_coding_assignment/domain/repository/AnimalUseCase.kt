package com.ghost.comcast_coding_assignment.domain.repository

import com.ghost.comcast_coding_assignment.data.model.AnimalListItemModel
import com.ghost.comcast_coding_assignment.utils.UiStatus
import kotlinx.coroutines.flow.Flow

interface AnimalUseCase {
    suspend fun execute(): Flow<UiStatus<List<AnimalListItemModel>>>
}