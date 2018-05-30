package com.example.vbm.kotlinapp;

import android.support.v7.app.AppCompatActivity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity :
        SimpleSearchFragment.OnFragmentInteractionListener,
        AdvancedSearchFragment.OnFragmentInteractionListener,
        AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.simple_search, SimpleSearchFragment.newInstance(), "rageComicList")
                    .add(R.id.simple_search, AdvancedSearchFragment.newInstance("1","2"), "rageComicList")
                    .commit()
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
    }
}
