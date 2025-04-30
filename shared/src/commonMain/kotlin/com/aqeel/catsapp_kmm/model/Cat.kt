package com.aqeel.catsapp_kmm.model

import kotlinx.serialization.Serializable

@Serializable
data class Cat(
    val id: String = "",
    val name: String,
    val breed: String,
    val age: Int,
    val imageUrl: String? = null,
    val description: String? = null
) 