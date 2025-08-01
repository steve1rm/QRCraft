package me.androidbox.qrcraft

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform