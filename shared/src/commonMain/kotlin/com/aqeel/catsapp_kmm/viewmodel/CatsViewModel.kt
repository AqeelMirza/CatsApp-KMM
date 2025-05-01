package com.aqeel.catsapp_kmm.viewmodel

import com.aqeel.catsapp_kmm.model.Cat
import com.aqeel.catsapp_kmm.repository.CatsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CatsViewModel(
    private val repository: CatsRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) : CatsViewModelInterface {
    override val cats: Flow<List<Cat>> = repository.getAllCats()

    override fun addCat(name: String, breed: String, age: Int, imageUrl: String?, description: String?) {
        val newCat = Cat(
            name = name,
            breed = breed,
            age = age,
            imageUrl = imageUrl,
            description = description
        )
        scope.launch {
            repository.addCat(newCat)
        }
    }

    override fun deleteCat(id: String) {
        scope.launch {
            repository.deleteCat(id)
        }
    }
} 