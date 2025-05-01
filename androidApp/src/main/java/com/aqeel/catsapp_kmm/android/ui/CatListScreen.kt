package com.aqeel.catsapp_kmm.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aqeel.catsapp_kmm.model.Cat
import com.aqeel.catsapp_kmm.viewmodel.AndroidCatsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatListScreen() {
    val viewModel = koinViewModel<AndroidCatsViewModel>()
    val cats by viewModel.cats.collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cats") },
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Cat")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(cats) { cat ->
                CatItem(
                    cat = cat,
                    onDelete = { viewModel.deleteCat(cat.id) }
                )
            }
        }

        if (showAddDialog) {
            AddCatDialog(
                onDismiss = { showAddDialog = false },
                onAdd = { name, breed, age ->
                    viewModel.addCat(name, breed, age)
                    showAddDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatItem(cat: Cat, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cat.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = cat.breed,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${cat.age} years old",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            cat.imageUrl?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = "Cat image",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(start = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }
            
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun AddCatDialog(
    onDismiss: () -> Unit,
    onAdd: (name: String, breed: String, age: Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Cat") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = breed,
                    onValueChange = { breed = it },
                    label = { Text("Breed") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val ageInt = age.toIntOrNull() ?: 0
                    if (name.isNotBlank() && breed.isNotBlank() && ageInt > 0) {
                        onAdd(name, breed, ageInt)
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
} 