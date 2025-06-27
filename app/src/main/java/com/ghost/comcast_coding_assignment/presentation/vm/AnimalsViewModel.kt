package com.ghost.comcast_coding_assignment.presentation.vm

import android.util.Log
import androidx.lifecycle.*
import com.ghost.comcast_coding_assignment.data.model.AnimalListItemModel
import com.ghost.comcast_coding_assignment.domain.repository.AnimalUseCase
import com.ghost.comcast_coding_assignment.utils.UiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalsViewModel @Inject constructor(
    private val animalUseCase: AnimalUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _allAnimals = MutableStateFlow<UiStatus<List<AnimalListItemModel>>>(UiStatus.Loading)
    val allAnimals: StateFlow<UiStatus<List<AnimalListItemModel>>> = _allAnimals

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

  //Updating search query
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        applyFilter()
    }
  //Loading animal data and exposing state to ui
    fun loadAnimals() {
        viewModelScope.launch(dispatcher) {
            animalUseCase.execute().collect { result ->
                when (result) {
                    is UiStatus.Loading -> _allAnimals.value = UiStatus.Loading
                    is UiStatus.Success -> {
                        cachedAnimalList = result.data
                        Log.d("AnimalsViewModel(Success)", "Loaded animals ...: $cachedAnimalList")
                        applyFilter()
                    }
                    is UiStatus.Error ->{
                        _allAnimals.value = UiStatus.Error(result.message)
                        Log.e("AnimalsViewModel(Error)", "Error loading animals: ${result.message}")
                    }

                }
            }
        }
    }


    private var cachedAnimalList: List<AnimalListItemModel> = emptyList()

    //Filtering list
    private fun applyFilter() {
        val query = _searchQuery.value.trim().lowercase()
        val filtered = if (query.isEmpty()) {
            cachedAnimalList
        } else {
            cachedAnimalList.filter {
                it.name.contains(query, true) ||
                        it.characteristics.commonName?.contains(query, true) == true
            }
        }
        _allAnimals.value = UiStatus.Success(filtered)
    }
}
