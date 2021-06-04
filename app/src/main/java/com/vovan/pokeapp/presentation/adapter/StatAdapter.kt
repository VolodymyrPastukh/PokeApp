package com.vovan.pokeapp.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vovan.pokeapp.databinding.PokeItemBinding
import com.vovan.pokeapp.databinding.StatItemBinding

class StatAdapter : RecyclerView.Adapter<StatAdapter.StatViewHolder>(){
    private var items = mutableListOf<PokemonStatItem>()

    fun setData(stats: List<PokemonStatItem>){
        items.clear()
        items.addAll(stats)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        return StatViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        val itemToShow = items[position]
        holder.bind(itemToShow)
    }

    override fun getItemCount(): Int = items.size


    class StatViewHolder(private val binding: StatItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: PokemonStatItem) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent: ViewGroup): StatViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StatItemBinding.inflate(layoutInflater, parent, false)
                return StatViewHolder(binding)
            }

        }
    }
}
