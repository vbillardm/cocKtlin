package com.hetic.cocktailapp.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.hetic.cocktailapp.R
import com.hetic.cocktailapp.adapter.CocktailCardAdapter
import com.hetic.cocktailapp.api.CocktailService
import kotlinx.android.synthetic.main.activity_main.*
import com.hetic.cocktailapp.api.RetrofitHelper
import com.hetic.cocktailapp.model.Cocktail
import com.hetic.cocktailapp.model.CocktailSource
import com.hetic.cocktailapp.model.Tag
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {

    private val TAG : String = "ITEMS"
    private lateinit var mItemsService : CocktailService
    private var mCocktails : List<Cocktail> = arrayListOf()
    private var mTags : List<Tag> = arrayListOf()

    companion object {
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //CONTENT
        mItemsService = RetrofitHelper.retrofit.create(CocktailService::class.java)
        initCocktailsList()
        initSearch()

        //ACTIONS
        initAdvancedSearchButton()
    }

    //INIT RECYCLER VIEW WITH CHOSEN ITEM LAYOUT AND ORIENTATION FOR TAGS
    private val mOrientation : Int = LinearLayoutManager.HORIZONTAL
    private val mTemplate : Int = R.layout.cocktail_card_item_horizontal

    fun initCocktailsList(){
        val adapter = CocktailCardAdapter(mTemplate) {
            goDetail(it.id)
        }
        cocktail_list.adapter = adapter
        cocktail_list.layoutManager = LinearLayoutManager(this, mOrientation,false)
        adapter.swapItems(arrayListOf())
        adapter.notifyDataSetChanged()

        val items = mItemsService.getCocktails("","","","","","")
        items.enqueue(object : Callback<ArrayList<Cocktail>> {
            override fun onResponse(call: Call<ArrayList<Cocktail>>?, response: Response<ArrayList<Cocktail>>?) {
                response?.body()?.let { source ->
                    if(source != null){
                        adapter.swapItems(source)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Cocktail>>?, t: Throwable?) { Log.d(TAG, "Unables : "+t.toString()) }
        })
    }

    private fun goDetail(id : Int){
        val detailIntent = DetailActivity.getIntent(this,id,null,false,null)
        startActivity(detailIntent)
    }


    fun initSearch(){
        val items = mItemsService.getAllCocktails("","","","","","1")
        items.enqueue(object : Callback<CocktailSource> {
            override fun onResponse(call: Call<CocktailSource>?, response: Response<CocktailSource>?) {
                Log.d("All",response?.body()?.toString())
                response?.body()?.let { source ->
                    mCocktails = source.cocktails
                    mTags = source.tags
                    Log.d("COCKTAILS",mCocktails.toString())
                    Log.d("TAGS",mTags.toString())
                    initAutomplete()
                }
            }

            override fun onFailure(call: Call<CocktailSource>?, t: Throwable?) { Log.d(TAG, "Unables : "+t.toString()) }
        })
    }

    fun initAutomplete(){
        val cocktailsName = mCocktails.map { i -> i.name }.toTypedArray()
        val cocktailsId = mCocktails.map { i -> i.id }.toTypedArray()

        val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                cocktailsName
        )
        input_search.setAdapter(adapter)

        input_search.onItemClickListener = AdapterView.OnItemClickListener{
            parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            goDetail(cocktailsId[position])
        }
    }

    fun initAdvancedSearchButton(){
        advanced_search.setOnClickListener{
            val advancedIntent = AdvancedSearchActivity.getIntent(this,mTags)
            startActivity(advancedIntent)
        }
    }
}
