package com.vovan.pokeapp.presentation.title

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.vovan.pokeapp.R
import com.vovan.pokeapp.databinding.FragmentTitleBinding
import com.vovan.pokeapp.presentation.MainActivity
import com.vovan.pokeapp.presentation.Navigation

class TitleFragment : Fragment() {

    private lateinit var viewModel: TitleViewModel
    private val navigator: Navigation? by lazy { (activity as? Navigation) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_title,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(TitleViewModel::class.java)

        binding.startButton.setOnClickListener {
            viewModel.startPlay()
        }


        viewModel.isStartPlay.observe(viewLifecycleOwner, { isStartPlay ->
            if(isStartPlay) {
                navigator?.openPokemonList()
                viewModel.resetPLay()
            }
        })
        return binding.root
    }

}