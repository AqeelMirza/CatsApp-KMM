package com.aqeel.catsapp_kmm.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ArrowDropDown
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
    val viewModel = koinViewModel<AndroidCatsViewModel>()
    var name by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var ageError by remember { mutableStateOf<String?>(null) }
    var showBreedDropdown by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Cat") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                // Name field
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Breed dropdown
                Box {
                    OutlinedTextField(
                        value = breed,
                        onValueChange = {},
                        label = { Text("Breed") },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { showBreedDropdown = true }) {
                                Icon(Icons.Default.ArrowDropDown, "Select breed")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    DropdownMenu(
                        expanded = showBreedDropdown,
                        onDismissRequest = { showBreedDropdown = false },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        viewModel.availableBreeds.forEach { breedOption ->
                            DropdownMenuItem(
                                text = { Text(breedOption) },
                                onClick = {
                                    breed = breedOption
                                    showBreedDropdown = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Age field with error handling
                TextField(
                    value = age,
                    onValueChange = { input ->
                        age = input
                        ageError = when {
                            input.isEmpty() -> null
                            input.toIntOrNull() == null -> "Please enter a valid number"
                            (input.toIntOrNull() ?: 0) <= 0 -> "Age must be greater than 0"
                            else -> null
                        }
                    },
                    label = { Text("Age (Months)") },
                    isError = ageError != null,
                    supportingText = {
                        if (ageError != null) {
                            Text(
                                text = ageError ?: "",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                    )
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
                },
                enabled = name.isNotBlank() && breed.isNotBlank() && ageError == null && age.isNotEmpty()
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