package com.vovan.pokeapp.presentation.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vovan.pokeapp.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    private var viewModel: TitleViewModel? = null
    private var _binding: FragmentTitleBinding? = null
    private val binding: FragmentTitleBinding
        get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTitleBinding.inflate(inflater).apply { _binding = this }.root

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