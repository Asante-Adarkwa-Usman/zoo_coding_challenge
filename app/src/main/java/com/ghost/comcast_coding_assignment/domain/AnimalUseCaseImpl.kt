package com.ghost.comcast_coding_assignment.domain

import com.ghost.comcast_coding_assignment.data.model.AnimalListItemModel
import com.ghost.comcast_coding_assignment.domain.repository.*
import com.ghost.comcast_coding_assignment.utils.UiStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnimalUseCaseImpl @Inject constructor(
    private val repository: AnimalRepository
) : AnimalUseCase {
   override suspend fun execute(): Flow<UiStatus<List<AnimalListItemModel>>> {
        return repository.getAllAnimals().map { status ->
            when (status) {
                is UiStatus.Loading -> UiStatus.Loading
                is UiStatus.Error -> UiStatus.Error(status.message)
                is UiStatus.Success -> {
                    val items = status.data.animals
                    UiStatus.Success(items)
                }
            }
        }
    }
}
