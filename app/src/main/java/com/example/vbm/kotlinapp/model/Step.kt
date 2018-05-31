package com.example.vbm.kotlinapp.model

data class Step(
        val nStep: Int,
        val title: String,
        val description: String = "",
        val imageDefault: String?,
        val videoUrl: String?,
        val ingredients: List<Ingredient> = listOf()
)
