package com.ghost.comcast_coding_assignment.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ghost.comcast_coding_assignment.data.model.AnimalListItemModel
import com.ghost.comcast_coding_assignment.presentation.components.AnimalList
import com.ghost.comcast_coding_assignment.presentation.vm.AnimalsViewModel
import com.ghost.comcast_coding_assignment.utils.UiStatus

@Composable
fun AnimalListScreen(viewModel: AnimalsViewModel, modifier: Modifier) {
    val state by viewModel.allAnimals.collectAsState()
    val query by viewModel.searchQuery.collectAsState()

    //Load animal's data upon screen load
    LaunchedEffect(Unit) {
        viewModel.loadAnimals()
    }

    Column {
        TextField(
            value = query,
            onValueChange = viewModel::updateSearchQuery,
            label = { Text("Search by name or common name") },
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        when (state) {
            is UiStatus.Loading -> Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            is UiStatus.Success -> {
                val animals = (state as UiStatus.Success<List<AnimalListItemModel>>).data
                AnimalList(animals)
            }
            is UiStatus.Error -> {
                val error = state as UiStatus.Error
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error: ${error.message}")
                    Spacer(modifier = modifier.height(8.dp))
                    Button(
                        onClick = {
                            viewModel.loadAnimals()
                        }
                    ) {
                        Text("Retry")
                    }
                    }
                }
            }

        }
    }
