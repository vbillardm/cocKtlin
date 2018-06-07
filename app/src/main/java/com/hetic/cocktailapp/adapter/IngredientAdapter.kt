package com.hetic.cocktailapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hetic.cocktailapp.R
import com.hetic.cocktailapp.model.Ingredient
import com.hetic.cocktailapp.utils.inflate
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientAdapter(val listener: (Ingredient) -> Unit) : RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    private var mItems: List<Ingredient> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.ingredient_item))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) = viewHolder.bind(mItems[position], listener)

    override fun getItemCount() = mItems.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Ingredient, listener: (Ingredient) -> Unit) = with(itemView) {
            name.text = item.getLabel()
        }
    }

    fun swapItems(items: List<Ingredient>) {
        mItems = items
    }
}
