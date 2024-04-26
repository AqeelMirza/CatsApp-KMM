package com.aqeel.catsapp_kmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform