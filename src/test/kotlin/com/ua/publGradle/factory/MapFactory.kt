package com.ua.publGradle.factory

interface MapFactory {
    fun create(): Map<String, String>
    fun createUpdated(): Map<String, String>
}