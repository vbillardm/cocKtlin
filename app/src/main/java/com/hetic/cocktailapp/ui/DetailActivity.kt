package com.hetic.cocktailapp.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.hetic.cocktailapp.R
import com.hetic.cocktailapp.api.CocktailService
import com.hetic.cocktailapp.api.RetrofitHelper
import com.hetic.cocktailapp.model.*
import com.hetic.cocktailapp.utils.displayMessage
import com.hetic.cocktailapp.utils.loadFragment
import com.hetic.cocktailapp.utils.replaceFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class DetailActivity : AppCompatActivity(),
        DetailIntroFragment.Listener,
        DetailEndFragment.Listener{

    private val TAG: String = "ITEM"
    private lateinit var mItem : CocktailDetail
    private var IS_RATED : Boolean = false

    companion object {
        val COCKTAIL_KEY = "cocktail_key"
        val COCKTAIL = "cocktail_obj"
        val END = "show_end_fragment"
        val RATED = "is_rated"

        @JvmStatic
        fun getIntent(context: Context, id: Int?, cocktail : CocktailDetail?, showEnd : Boolean?, rated : Boolean?): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            if(id != null){
                intent.putExtra(COCKTAIL_KEY, id)
            }else{
                intent.putExtra(COCKTAIL, cocktail as Serializable)
            }
            intent.putExtra(END,showEnd)
            intent.putExtra(RATED,rated)
            return intent
        }
    }


    private fun loadById(id: Int, root : Boolean) {
        val itemsService = RetrofitHelper.retrofit.create(CocktailService::class.java)
        val item = itemsService.getCocktail(id)
        item.enqueue(object : Callback<CocktailDetail> {
            override fun onResponse(call: Call<CocktailDetail>?, response: Response<CocktailDetail>?) {
                Log.d("Cocktail",response.toString())
                response?.body()?.let { source ->
                    mItem = source
                    initFragment(root)
                }
            }

            override fun onFailure(call: Call<CocktailDetail>?, t: Throwable?) { Log.d(TAG, "Unable") }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //Load
        val root = intent.getBooleanExtra(END,false)
        IS_RATED = intent.getBooleanExtra(RATED, false)

        if(intent.hasExtra(COCKTAIL_KEY)){
            val id = intent.getIntExtra(COCKTAIL_KEY, 0)
            loadById(id,root)
        }else{
            mItem = intent.getSerializableExtra(COCKTAIL) as CocktailDetail
            initFragment(root)
        }
    }

    private fun initFragment(root : Boolean){
        if(root) {
            loadFragment(supportFragmentManager, DetailEndFragment.newInstance(mItem,IS_RATED), R.id.fragments)
        }else{
            loadFragment(supportFragmentManager, DetailIntroFragment.newInstance(mItem), R.id.fragments)
        }
    }

    private fun goToHome(){
        Log.d("RATED ?",IS_RATED.toString())
        val intent = MainActivity.getIntent(this)
        startActivity(intent)
    }

    private fun goToSteps(){
        Log.d("RATED ?",IS_RATED.toString())
        val intent = StepsActivity.getIntent(this,mItem,IS_RATED)
        startActivity(intent)
    }

    private fun setRate(root : String, rate : Float){
        Log.d("Rate",rate.toString())
        if(IS_RATED || rate == 0F) {  setEndRoot(root) }else {
            val service = RetrofitHelper.retrofit.create(CocktailService::class.java)
            val rateService = service.setRate(mItem.id.toString(), rate.toString())
            rateService.enqueue(object : Callback<CocktailDetail> {
                override fun onResponse(call: Call<CocktailDetail>?, response: Response<CocktailDetail>?) {
                    Log.d("RATE", response.toString())
                    response?.body()?.let { source ->
                        displayMessage("Votre note a été prise en compte", this@DetailActivity)
                        mItem = source
                        IS_RATED = true
                        setEndRoot(root)
                    }
                }

                override fun onFailure(call: Call<CocktailDetail>?, t: Throwable?) {
                    displayMessage("Une erreur s'est produite, merci de réessayer.", this@DetailActivity)
                }
            })
        }
    }

    private fun setEndRoot(destination: String){
        if (destination == "intro") { replaceFragment(supportFragmentManager,DetailIntroFragment.newInstance(mItem),R.id.fragments) }
        else if (destination == "steps") { goToSteps() }
        else { goToHome() }
    }

    override fun fragmentDetailIntroCallback(launchSteps: Boolean) { goToSteps() }
    override fun fragmentDetailEndCallback(destination: String, value : Float?) { setRate(destination,value ?: 0F) }

}
