package com.vovan.pokeapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.vovan.pokeapp.R
import timber.log.Timber

private const val ITEM_TYPE_UNKNOWN = 0
private const val ITEM_TYPE_POKEMON = 1
private const val ITEM_TYPE_HEADER = 2

class PokeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: MutableList<DisplayableItem> = emptyList<DisplayableItem>().toMutableList()

    fun setPokemonList(data: List<DisplayableItem>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_POKEMON -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.poke_item, parent, false)
                PokemonViewHolder(view)
            }

            ITEM_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_item, parent, false)
                BannerViewHolder(view)
            }

            else -> {
                throw IllegalStateException("Shos ne to")
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val itemToShow = items[position]) {
            is PokeItem -> (holder as PokemonViewHolder).bind(itemToShow)
            is HeaderItem -> (holder as BannerViewHolder).bind(itemToShow)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is PokeItem -> ITEM_TYPE_POKEMON
            is HeaderItem -> ITEM_TYPE_HEADER
            else -> ITEM_TYPE_UNKNOWN
        }
    }

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = itemView.findViewById<TextView>(R.id.name)
        private val imagePreview = itemView.findViewById<ImageView>(R.id.imagePreview)
        private val layout = itemView.findViewById<LinearLayout>(R.id.linearLayout)

        fun bind(item: PokeItem) {
            textView.text = item.name

            if (changePosition) {
                changePosition = false
                val temp = layout.getChildAt(0)
                layout.removeViewAt(0)
                layout.addView(temp)
            }else{
                changePosition = true
            }


            Picasso.get().load(item.imageUrl).into(imagePreview, object : Callback {
                override fun onSuccess() {
                    Timber.d("Loaded image")
                }

                override fun onError(e: Exception?) {
                    Timber.d("Loaded image exception $e")
                }
            })
        }

        companion object{
            private var changePosition = true
        }

    }

    class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = itemView.findViewById<TextView>(R.id.bannerName)

        fun bind(item: HeaderItem) {
            textView.text = item.text.toString()

            when (item.text) {
                HeaderItem.Attribute.STRENGTH -> changeColor(Color.RED)
                HeaderItem.Attribute.AGILITY -> changeColor(Color.GREEN)
                HeaderItem.Attribute.INTELLIGENCE -> changeColor(Color.CYAN)
                else -> changeColor(Color.GRAY)
            }
        }

        private fun changeColor(color: Int){
            itemView.setBackgroundColor(color)
            textView.setBackgroundColor(color)
        }
    }


}