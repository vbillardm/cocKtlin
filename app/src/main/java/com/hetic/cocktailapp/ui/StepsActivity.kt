package com.hetic.cocktailapp.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hetic.cocktailapp.R
import com.hetic.cocktailapp.model.CocktailDetail
import com.hetic.cocktailapp.model.Step
import kotlinx.android.synthetic.main.activity_steps.*
import java.io.Serializable

class StepsActivity : AppCompatActivity(), StepFragment.Listener {

    private lateinit var mItem: CocktailDetail
    private lateinit var mSteps : List<Step>
    private var IS_RATED : Boolean = false

    /**
     * Create new instance of Fragment and set params from activity
     */
    companion object {
        private const val COCKTAIL = "cocktail"
        private const val RATED = "cocktail_is_rated"

        @JvmStatic
        fun getIntent(context: Context, cocktail : CocktailDetail,rated : Boolean?): Intent {
            val intent = Intent(context, StepsActivity::class.java)
            intent.putExtra(COCKTAIL, cocktail as Serializable)
            intent.putExtra(RATED,rated)
            return intent
        }
    }

    /**
     * Init and populate view with data
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_steps)
        mItem = intent.getSerializableExtra(COCKTAIL) as CocktailDetail
        mSteps = mItem.steps.sortedBy{ it.n_step.toInt() }
        IS_RATED = intent.getBooleanExtra(RATED,false)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager) //Init page adapter to swipe between Slides
        steps_fragments.adapter = mSectionsPagerAdapter
        initButtonNavigation()
    }

    /**
     * Navigation action depending on Slides position
     */
    private fun initButtonNavigation(){
        previous_step.setOnClickListener {
            if (steps_fragments.currentItem == 0) { goToDetail(false) }
            else { steps_fragments.currentItem = steps_fragments.currentItem - 1 }
            progress(steps_fragments.currentItem)
        }

        next_step.setOnClickListener {
            if (steps_fragments.currentItem < mSteps.size - 1) {
                steps_fragments.currentItem = steps_fragments.currentItem + 1
            } else {
                goToDetail(true)
            }
            progress(steps_fragments.currentItem)
        }
    }

    private fun progress(position : Int){
        val progress = (position.toFloat() / mSteps.size.toFloat() * 100)
        steps_progress.progress = progress.toInt()
        steps_progress_text.text = "%.0f".format(progress) + "%"
    }

    /**
     * Init detailIntent with fragment to show depending on Slide
     */
    private fun goToDetail(showEnd : Boolean){
        val detailIntent = DetailActivity.getIntent(this,null,mItem,showEnd, IS_RATED)
        startActivity(detailIntent)
    }

    /**
     * Init images by callback
     */
    override fun fragmentStepInit(image: String) {
        Glide.with(this).load(image).into(step_image)
    }

    //Page Adapter to swipe between Fragments
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            progress(steps_fragments.currentItem)
            return StepFragment.newInstance(mSteps[position])
        }
        override fun getCount(): Int { return mSteps.size }
    }
}
