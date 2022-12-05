package com.vovan.pokeapp.presentation.pokelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vovan.pokeapp.databinding.FragmentPokemonListBinding
import com.vovan.pokeapp.presentation.adapter.PokemonAdapter
import com.vovan.pokeapp.presentation.adapter.PokemonItem
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PokemonListFragment : Fragment() {

    private val viewModel: RxPokemonListViewModel by viewModel()
    private var _binding: FragmentPokemonListBinding? = null
    private val binding: FragmentPokemonListBinding
        get() = checkNotNull(_binding)
    private var adapter: PokemonAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPokemonListBinding.inflate(inflater).apply { _binding = this }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PokemonAdapter(object : PokemonAdapter.PokemonClickListener {
            override fun onClick(item: PokemonItem) {
                findNavController().navigate(
                    PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailsFragment(item.id)
                )
            }

            override fun onLongClick(item: PokemonItem): Boolean {
                Toast.makeText(requireContext(), "Pokemon[${item.name} stored]", Toast.LENGTH_SHORT).show()
                viewModel.storeItem(item)
                return true
            }

            override fun onStarClick(item: PokemonItem) {
                viewModel.deleteStoredItem(item)
                Toast.makeText(requireContext(), "Pokemon[${item.name} deleted]", Toast.LENGTH_SHORT).show()
            }

        })
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }

        viewModel.state.observe(viewLifecycleOwner, ::displayData)
    }


    private fun displayData(state: PokemonListViewState) = with(binding) {
        when (state) {
            is PokemonListViewState.Loading -> {}

            is PokemonListViewState.Data -> {
                progressBar.hide()
                adapter?.submitList(state.pokemons)
            }

            is PokemonListViewState.Error -> {
                Timber.e(state.message)
            }
        }
    }

}