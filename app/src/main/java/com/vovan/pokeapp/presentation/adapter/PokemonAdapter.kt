package com.vovan.pokeapp.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vovan.pokeapp.R
import com.vovan.pokeapp.databinding.PokeItemBinding

private const val ITEM_TYPE_UNKNOWN = 0
private const val ITEM_TYPE_POKEMON = 1
private const val ITEM_TYPE_HEADER = 2

class PokemonAdapter(
    private val clickListener: PokemonClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: MutableList<DisplayableItem> = emptyList<DisplayableItem>().toMutableList()

    fun setPokemonList(data: List<DisplayableItem>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_POKEMON -> PokemonViewHolder.from(parent)
            ITEM_TYPE_HEADER -> HeaderViewHolder.from(parent)
            else -> throw IllegalStateException("Shos ne to")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val itemToShow = items[position]) {
            is PokemonItem -> (holder as PokemonViewHolder).bind(itemToShow, clickListener)
            is HeaderItem -> (holder as HeaderViewHolder).bind(itemToShow)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is PokemonItem -> ITEM_TYPE_POKEMON
            is HeaderItem -> ITEM_TYPE_HEADER
            else -> ITEM_TYPE_UNKNOWN
        }
    }

    class PokemonViewHolder(val binding: PokeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val layout = binding.linearLayout

        fun bind(item: PokemonItem, clickListener: PokemonClickListener) {
            binding.item = item
            binding.executePendingBindings()
            binding.click = clickListener

            if (changePosition) {
                changePosition = false
                val temp = layout.getChildAt(0)
                layout.removeViewAt(0)
                layout.addView(temp)
            } else {
                changePosition = true
            }
        }

        companion object {
            private var changePosition = true

            fun from(parent: ViewGroup): PokemonViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PokeItemBinding.inflate(layoutInflater, parent, false)
                return PokemonViewHolder(binding)
            }

        }

    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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

        private fun changeColor(color: Int) {
            itemView.setBackgroundColor(color)
            textView.setBackgroundColor(color)
        }

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header_item, parent, false)
                return HeaderViewHolder(view)
            }

        }
    }

    class PokemonClickListener(val clickListener: (id: Int) -> Unit) {
        fun onClick(item: PokemonItem) = clickListener(item.id)
    }
}