package com.aqeel.catsapp_kmm.repository

import com.aqeel.catsapp_kmm.model.Cat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class InMemoryCatsRepository : CatsRepository {
    private val cats = MutableStateFlow<List<Cat>>(emptyList())

    override fun getAllCats(): Flow<List<Cat>> {
        println("Getting all cats: ${cats.value}")
        return cats.asStateFlow()
    }

    override suspend fun addCat(cat: Cat) {
        println("Adding cat: $cat")
        val newCat = cat.copy(id = generateId())
        println("Generated new cat with ID: ${newCat.id}")
        cats.update { currentCats ->
            val updatedList = currentCats + newCat
            println("Updated cats list: $updatedList")
            updatedList
        }
    }

    override suspend fun deleteCat(id: String) {
        println("Deleting cat with ID: $id")
        cats.update { currentCats ->
            val updatedList = currentCats.filter { it.id != id }
            println("Updated cats list after deletion: $updatedList")
            updatedList
        }
    }

    override suspend fun getCat(id: String): Cat? {
        println("Getting cat with ID: $id")
        return cats.value.find { it.id == id }
    }

    private fun generateId(): String {
        val id = Random.nextInt(100000).toString()
        println("Generated ID: $id")
        return id
    }
} 