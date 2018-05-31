package com.example.vbm.kotlinapp.model

data class Ingredient(
        val id: Int,
        val name: String,
        val quantity: Float,
        val unity: String = ""
)