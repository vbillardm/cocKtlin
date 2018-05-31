package com.example.vbm.kotlinapp.model

data class Cocktail(
        val id: Int,
        val name: String,
        val tags: List<String>,
        val imageUrl: String,
        val ingredients: List<Ingredient> = listOf(),
        val steps: List<Step> = listOf(),
        val globalRate: Float?,
        val difficultyRate: Float?,
        val speedRate: Float?,
        val priceRate: Float?,
        val nbRate: Int = 0,
        val suggestions: List<Cocktail> = listOf()
)