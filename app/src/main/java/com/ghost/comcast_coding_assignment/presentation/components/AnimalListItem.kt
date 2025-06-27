package com.ghost.comcast_coding_assignment.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ghost.comcast_coding_assignment.data.model.AnimalListItemModel
import com.ghost.comcast_coding_assignment.utils.AnimalType

@Composable
fun AnimalListItem(animal: AnimalListItemModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Name: ${animal.name}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Phylum: ${animal.taxonomy.phylum}", style = MaterialTheme.typography.bodySmall)
            Text("Scientific Name: ${animal.taxonomy.scientificName ?: "N/A"} ", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(8.dp))

            when (animal.type) {
                AnimalType.DOG -> {
                    Text("Slogan: ${animal.characteristics.slogan ?: "N/A"}")
                    Text("Lifespan: ${animal.characteristics.lifespan ?: "N/A"}")
                }
                AnimalType.BIRD -> {
                    Text("Wingspan: ${animal.characteristics.wingspan ?: "N/A"}")
                    Text("Habitat: ${animal.characteristics.habitat ?: "N/A"}")
                }
                AnimalType.BUG -> {
                    Text("Prey: ${animal.characteristics.prey ?: "N/A"}")
                    Text("Predators: ${animal.characteristics.predators ?: "N/A"}")
                }
            }
        }
    }
}

