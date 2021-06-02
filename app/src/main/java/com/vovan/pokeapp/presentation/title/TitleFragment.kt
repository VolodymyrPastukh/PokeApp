package com.vovan.pokeapp.presentation.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vovan.pokeapp.R
import com.vovan.pokeapp.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    private var viewModel: TitleViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_title,
            container,
            false
        )

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
            return binding.root


    }

}