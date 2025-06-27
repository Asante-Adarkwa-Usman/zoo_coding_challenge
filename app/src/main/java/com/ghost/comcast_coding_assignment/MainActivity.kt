package com.ghost.comcast_coding_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.ghost.comcast_coding_assignment.presentation.screens.AnimalListScreen
import com.ghost.comcast_coding_assignment.presentation.vm.AnimalsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //Initializing view model
            val viewModel: AnimalsViewModel = hiltViewModel()
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Animal Explorer", color = Color.White) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            ) { padding ->
                AnimalListScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}