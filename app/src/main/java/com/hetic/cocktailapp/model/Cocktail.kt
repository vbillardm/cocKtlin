package com.hetic.cocktailapp.model

import android.util.Log
import java.io.Serializable


data class Cocktail  (
        val id: Int,
        val name: String,
        val image: String,
        val url: String,
        var globalRateVotes : String,
        var GlobalRate : Float,
        var tags : ArrayList<Tag> = arrayListOf()) : Serializable {

    fun getRating() : Float  {
        val nbRates = globalRateVotes.toFloat()
        return if (nbRates == 0F) 0F else GlobalRate/nbRates
    }

    fun getTimeRating() : Float{
        return 0F
    }

    fun getDifficultyRating() : Float{
        return 0F
    }

    fun getPriceRating() : Float{
        return 0F
    }

    fun getFilteredTags(i : Int) : List<Tag> {
        if(tags.size <= 0){ return arrayListOf() }
        val tags = tags.filter{ i -> i.type != "context"}
        return tags.subList(1,if(tags.size > (i-1)){i}else{tags.size})
    }

    fun getRate(value : String,votes : String) : Rate { return Rate(value,votes)  }
}