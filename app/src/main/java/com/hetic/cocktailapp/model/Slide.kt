package com.hetic.cocktailapp.model

import java.io.Serializable


data class Slide(
        var id : Int,
        var type : String,
        var tags: List<Tag> = arrayListOf(),
        var activatedTags: MutableList<Int> = arrayListOf()) : Serializable