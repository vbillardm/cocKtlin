package com.example.vbm.kotlinapp;

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

import com.example.vbm.kotlinapp.CocktailFragment.OnListFragmentInteractionListener
import com.example.vbm.kotlinapp.CocktailContent
import com.example.vbm.kotlinapp.CocktailContent.CocktailItem

import kotlinx.android.synthetic.main.fragment_cocktail.view.*

class MyCocktailRecyclerViewAdapter(
        private val mValues: List<CocktailItem>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<MyCocktailRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as CocktailItem
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_cocktail, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mNameView.text = item.name
        holder.mNoteGeneraleView.text = item.note_generale
        holder.mNoteDureeView.text = item.note_duree
        holder.mNotePersonneView.text = item.personnes
        holder.mNotePrixView.text = item.note_prix
        holder.mNoteDifficulteView.text = item.note_difficulte
        Picasso.with(holder.mImageView.getContext()).load(item.image).into(holder.mImageView);

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mNameView: TextView = mView.item_name
        val mNoteGeneraleView: TextView = mView.item_note_generale
        val mNoteDureeView: TextView = mView.item_note_duree
        val mNotePersonneView: TextView = mView.item_personnes
        val mNotePrixView: TextView = mView.item_note_prix
        val mNoteDifficulteView: TextView = mView.item_note_difficulte
        val mImageView: ImageView = mView.item_image

    }
}
