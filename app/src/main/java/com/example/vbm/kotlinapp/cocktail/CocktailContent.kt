package com.example.vbm.kotlinapp;

import android.media.Image
import android.net.Uri
import java.util.ArrayList
import java.util.HashMap

object CocktailContent {

    val ITEMS: MutableList<CocktailItem> = ArrayList()
    val ITEM_MAP: MutableMap<String, CocktailItem> = HashMap()

    private val COUNT = 25

    init {
        for (i in 1..COUNT) {
            addItem(createCocktail(i))
        }
    }

    private fun addItem(item: CocktailItem) {
        ITEMS.add(item)
        ITEM_MAP.put(
                item.id,
                item
        )
    }

    private fun createCocktail(position: Int): CocktailItem {
        return CocktailItem(
                position.toString(),
                "Cocktail " + position,
                "XXXXX",
                5.toString(),
                5.toString(),
                5.toString(),
                200.toString(),
                Uri.parse("http://www.ipp.org/wp-content/uploads/ipp-summer-1.png")
        )
    }

    data class CocktailItem(val id: String,
                            val name: String,
                            val note_generale: String,
                            val note_duree: String,
                            val note_prix: String,
                            val note_difficulte: String,
                            val personnes: String,
                            val image : Uri) {
    }
}