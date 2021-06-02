package com.vovan.pokeapp.presentation.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vovan.pokeapp.R
import com.vovan.pokeapp.databinding.FragmentTitleBinding

class TitleFragment : Fragment(R.layout.fragment_title) {

    private var viewModel: TitleViewModel? = null
    private val binding: FragmentTitleBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(TitleViewModel::class.java)

        binding.startButton.setOnClickListener {
            viewModel?.startPlay()
        }

        viewModel?.let {
            it.isStartPlay.observe(viewLifecycleOwner) { isStartPlay ->
                if (isStartPlay) {
                    findNavController().navigate(
                        TitleFragmentDirections.actionTitleFragmentToPokemonListFragment()
                    )
                    viewModel?.resetPLay()
                }
            }
        }
    }
}