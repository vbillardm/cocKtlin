package com.example.vbm.kotlinapp;

import android.net.Uri

class Cocktail(val id: String,
               val name: String,
               val note_generale: String,
               val note_duree: String,
               val note_prix: String,
               val note_difficulte: String,
               val personnes: String,
               val image : Uri) {
}