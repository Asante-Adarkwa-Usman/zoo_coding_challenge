package com.ghost.comcast_coding_assignment.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
            Text("Scientific Name: ${animal.taxonomy.scientificName}", style = MaterialTheme.typography.bodySmall)

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

