package com.aqeel.catsapp_kmm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aqeel.catsapp_kmm.repository.CatsRepository
import kotlinx.coroutines.launch

class AndroidCatsViewModel(
    repository: CatsRepository
) : ViewModel(), CatsViewModelInterface {
    
    private val commonViewModel = CatsViewModel(repository)
    
    override val cats = commonViewModel.cats
    
    override fun addCat(name: String, breed: String, age: Int, imageUrl: String?, description: String?) {
        println("AndroidCatsViewModel: Adding cat - Name: $name, Breed: $breed, Age: $age")
        viewModelScope.launch {
            commonViewModel.addCat(name, breed, age, imageUrl, description)
        }
    }
    
    override fun deleteCat(id: String) {
        println("AndroidCatsViewModel: Deleting cat with ID: $id")
        viewModelScope.launch {
            commonViewModel.deleteCat(id)
        }
    }
} 