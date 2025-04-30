package com.aqeel.catsapp_kmm.repository

import com.aqeel.catsapp_kmm.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatsRepository {
    fun getAllCats(): Flow<List<Cat>>
    suspend fun addCat(cat: Cat)
    suspend fun deleteCat(id: String)
    suspend fun getCat(id: String): Cat?
} 