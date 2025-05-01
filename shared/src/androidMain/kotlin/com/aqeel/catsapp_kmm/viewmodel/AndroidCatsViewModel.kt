package com.aqeel.catsapp_kmm.viewmodel

import androidx.lifecycle.ViewModel
import com.aqeel.catsapp_kmm.repository.CatsRepository

class AndroidCatsViewModel(
    repository: CatsRepository
) : ViewModel(), CatsViewModelInterface {
    
    private val commonViewModel = CatsViewModel(repository)
    
    override val cats = commonViewModel.cats
    
    override fun addCat(name: String, breed: String, age: Int, imageUrl: String?, description: String?) {
        commonViewModel.addCat(name, breed, age, imageUrl, description)
    }
    
    override fun deleteCat(id: String) {
        commonViewModel.deleteCat(id)
    }
} 