package com.vovan.pokeapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vovan.pokeapp.createRandGradientBackground
import com.vovan.pokeapp.databinding.PokeItemBinding


class PokemonAdapter(
    private val clickListener: PokemonClickListener
) : ListAdapter<PokemonItem, PokemonAdapter.PokemonViewHolder>(PokemonItemDiffCallback) {

    //Create
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder.from(parent)
    }

    //Bind
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val itemToShow = getItem(position)
        holder.bind(itemToShow, clickListener)
    }

    class PokemonViewHolder(private val binding: PokeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val layout = binding.linearLayout

        fun bind(item: PokemonItem, clickListener: PokemonClickListener) = with(binding) {
            layout.background = createRandGradientBackground()
            this.item = item
            executePendingBindings()
            click = clickListener

            starIv.visibility = item.getStoredVisibility()
            starIv.setOnClickListener {
                starIv.visibility = View.GONE
                clickListener.onStarClick(item)
            }

            itemView.setOnLongClickListener {
                starIv.visibility = View.VISIBLE
                clickListener.onLongClick(item)
            }

            //Change position of each next element
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


    private object PokemonItemDiffCallback : DiffUtil.ItemCallback<PokemonItem>() {
        override fun areItemsTheSame(
            oldItem: PokemonItem,
            newItem: PokemonItem
        ): Boolean = oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: PokemonItem,
            newItem: PokemonItem
        ): Boolean = oldItem == newItem
    }


    interface PokemonClickListener {
        fun onClick(item: PokemonItem)
        fun onLongClick(item: PokemonItem): Boolean
        fun onStarClick(item: PokemonItem)
    }
}