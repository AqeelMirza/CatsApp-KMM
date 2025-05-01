package com.aqeel.catsapp_kmm.viewmodel

import kotlinx.coroutines.flow.Flow
import com.aqeel.catsapp_kmm.model.Cat

interface CatsViewModelInterface {
    val cats: Flow<List<Cat>>
    fun addCat(name: String, breed: String, age: Int, imageUrl: String? = null, description: String? = null)
    fun deleteCat(id: String)
} 