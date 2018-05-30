package com.example.vbm.kotlinapp;

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import com.example.vbm.kotlinapp.CocktailContent
import java.util.ArrayList


class FavoritesActivity :
        CocktailFragment.OnListFragmentInteractionListener,
        AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
    }
    override fun onListFragmentInteraction(item: CocktailContent.CocktailItem?) {
    }
}

/*package com.example.alicesevin.cocktlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.alicesevin.cocktlin.cocktail.*
import android.support.v4.view.ViewCompat
import android.support.v4.app.ActivityOptionsCompat
import android.content.Intent
import java.util.ArrayList


class FavoritesActivity : AppCompatActivity() {

    val ITEMS: MutableList<Cocktail> = ArrayList()
    private val COUNT = 25

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        addCocktails()

        // Creates a vertical Layout Manager
        rv_animal_list.layoutManager = LinearLayoutManager(this)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
//        rv_animal_list.layoutManager = GridLayoutManager(this, 2)

        // Access the RecyclerView Adapter and load the data into it
        rv_animal_list.adapter = AnimalAdapter(animals, this)
    }

    fun addCocktails() {
        for (i in 1.. COUNT) {
            addItem(Cocktail.create())
        }
    }
}
*/