package com.example.koinannotations.domain.modelo


data class Actor(
    val id: Int,
    val name: String,
    val character: String,
    val profile: String? = null,
    val department: String,
)